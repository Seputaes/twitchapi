package gg.sep.twitchapi.helix.api.videos;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.api.AbstractPaginatedAPI;
import gg.sep.twitchapi.helix.endpoint.VideosEndpoint;
import gg.sep.twitchapi.helix.model.video.Video;
import gg.sep.twitchapi.helix.model.video.Videos;

/**
 * Implementation of the Twitch Helix Videos API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-videos
 */
@Log4j2
public class VideosAPI extends AbstractPaginatedAPI<Video, Videos> {

    @Getter
    private VideosEndpoint videosEndpoint;

    /**
     * Construct the Helix Videos API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Videos API.
     */
    public VideosAPI(final Helix helix) {
        super(helix);
        this.videosEndpoint = helix.getRetrofit().create(VideosEndpoint.class);
    }

    /**
     * Gets a specific Twitch Video by its ID.
     * @param id ID of the video.
     * @return Optional of the video if it is found, otherwise empty.
     */
    public Optional<Video> getVideoById(final String id) {
        final List<Video> videos = getVideosById(Collections.singletonList(id));
        return (videos.isEmpty()) ? Optional.empty() : Optional.of(videos.get(0));
    }

    /**
     * Gets a list of Twitch Videos for the specified Video IDs.
     * @param ids List of Video IDs to retrieve.
     * @return List of videos. IDs that are not found will not be included in the list.
     */
    public List<Video> getVideosById(final List<String> ids) {
        return performPagination(getCallParams(ids, null, null, null, null, null, null), 0);
    }

    /**
     * Gets a list of Twitch Videos for a specific Twitch Broadcaster/User by their ID.
     * @param broadcasterId ID of the Twitch Broadcaster/User/Channel.
     * @return List of videos for the specified Twitch Broadcaster/User.
     */
    public List<Video> getVideosByBroadcaster(final String broadcasterId) {
        return performPagination(getCallParams(null, broadcasterId, null, null, null, null, null), 0);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    protected Optional<Videos> innerCall(final Map<String, Object> callParams, final int first, final String cursor) {
        try {
            final List<String> ids = (List<String>) callParams.get("ids");
            final String userId = (String) callParams.get("userId");
            final String gameId = (String) callParams.get("gameId");
            final String language = (String) callParams.get("language");
            final String type = (String) callParams.get("type");
            final String period = (String) callParams.get("period");
            final String sort = (String) callParams.get("sort");
            final Call<Videos> call = videosEndpoint.getVideos(ids, userId, gameId, language, type, period,
                sort, first, cursor);
            return executePaginatedCall(call);
        } catch (final ClassCastException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, String)}.
     */
    private Map<String, Object> getCallParams(final List<String> ids, final String userId, final String gameId,
                                              final String language, final String type,
                                              final String period, final String sort) {
        final Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("userId", userId);
        map.put("gameId", gameId);
        map.put("language", language);
        map.put("type", type);
        map.put("period", period);
        map.put("sort", sort);
        return map;
    }
}
