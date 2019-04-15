package gg.sep.twitchapi.helix.api.streams;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.api.AbstractPaginatedAPI;
import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.endpoint.StreamsEndpoint;
import gg.sep.twitchapi.helix.model.stream.Stream;
import gg.sep.twitchapi.helix.model.stream.Streams;
import gg.sep.twitchapi.helix.model.tag.Tag;

/**
 * Implementation of the Twitch Helix Streams API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-streams
 *
 * TODO: Implement batching for when the number of query parameters > 100
 */
@Log4j2
public class StreamsAPI extends AbstractPaginatedAPI<Stream, Streams> {

    @Getter
    private StreamsEndpoint streamsEndpoint;

    @Getter(lazy = true)
    private final StreamTagsAPI streamTagsAPI = initStreamTagsAPI();

    /**
     * Construct the Helix Streams API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Streams API.
     */
    public StreamsAPI(final Helix helix) {
        super(helix);
        this.streamsEndpoint = helix.getRetrofit().create(StreamsEndpoint.class);
    }

    /**
     * Gets a list of the current top 1000 live Streams.
     * @return Current Top 1000 live Streams.
     */
    public List<Stream> getTopStreams() {
        return getTopStreams(1000);
    }

    /**
     * Gets a list of the current top live streams, up to the maximum specified in {@code limit}.
     * @param limit Maximum number of results to return.
     * @return List of the current top live streams, up to the maximum specified in {@code limit}.
     */
    public List<Stream> getTopStreams(final int limit) {
        return performPagination(getCallParams(null, null, null, null, null), (double) limit);
    }

    /**
     * Gets a list of currently live Streams for the specified list of User Logins.
     * @param userLogins List of User Logins.
     * @return :ist of currently live Streams for the specified list of User Logins.
     */
    public List<Stream> getStreamsByUserLogin(final List<String> userLogins) {
        return performPagination(getCallParams(null, userLogins, null, null, null), 0);
    }

    /**
     * Gets a live Stream by a user/broadcaster ID.
     * @param userId ID of the user/broadcaster.
     * @return Optional of the Stream if it is live, otherwise empty if it is offline or an invalid user.
     */
    public Optional<Stream> getStreamByUserId(final String userId) {
        final List<Stream> streams = getStreamsByUserId(Collections.singletonList(userId));
        return (streams.isEmpty()) ? Optional.empty() : Optional.of(streams.get(0));
    }

    /**
     * Gets a live Stream by a user/broadcaster Login name.
     * @param userLogin Login name of the user/broadcaster.
     * @return Optional of the Stream if it is live, otherwise empty if it is offline or an invalid user.
     */
    public Optional<Stream> getStreamByUserLogin(final String userLogin) {
        final List<Stream> streams = getStreamsByUserLogin(Collections.singletonList(userLogin));
        return (streams.isEmpty()) ? Optional.empty() : Optional.of(streams.get(0));
    }

    /**
     * Gets a list of currently live Streams for the specified list of User IDs.
     * @param userIds List of User IDs.
     * @return :ist of currently live Streams for the specified list of User IDs.
     */
    public List<Stream> getStreamsByUserId(final List<String> userIds) {
        return performPagination(getCallParams(userIds, null, null, null, null), 0);
    }

    /**
     * Gets a list of tags currently on the specified broadcaster's Stream.
     * @param broadcasterId Broadcaster/Channel/User ID for which to get current Stream Tags.
     * @return List of tags currently on the specified broadcaster's Stream.
     */
    public List<Tag> getTagsForStream(final String broadcasterId) {
        return getStreamTagsAPI().getTagsForStream(broadcasterId);
    }

    @SuppressWarnings("unchecked")
    protected Optional<Streams> innerCall(final Map<String, Object> callParams, final int first, final String cursor) {
        try {
            final List<String> userIds = (List<String>) callParams.get("userIds");
            final List<String> userLogins = (List<String>) callParams.get("userLogins");
            final String gameId = (String) callParams.get("gameId");
            final String language = (String) callParams.get("language");
            final List<String> communityIds = (List<String>) callParams.get("communityIds");
            final Call<Streams> call = streamsEndpoint.getStreams(userIds, userLogins, gameId, language, communityIds,
                first, cursor);
            return executePaginatedCall(call);
        } catch (final ClassCastException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, String)}.
     */
    private Map<String, Object> getCallParams(final List<String> userIds, final List<String> userLogins,
                                              final String gameId, final String language,
                                              final List<String> communityIds) {
        final Map<String, Object> map = new HashMap<>();
        map.put("userIds", userIds);
        map.put("userLogins", userLogins);
        map.put("gameId", gameId);
        map.put("language", language);
        map.put("communityIds", communityIds);
        return map;
    }

    /**
     * Initialize a new child StreamTagsAPI from the current Streams API.
     * @return new StreamTags API based on this Streams API.
     */
    private StreamTagsAPI initStreamTagsAPI() {
        return new StreamTagsAPI(this.getHelix());
    }
}
