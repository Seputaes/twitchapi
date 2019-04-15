package gg.sep.twitchapi.kraken.api.channels;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.kraken.api.AbstractPaginatedAPI;
import gg.sep.twitchapi.kraken.endpoint.ChannelsEndpoint;
import gg.sep.twitchapi.kraken.model.channel.ChannelVideos;
import gg.sep.twitchapi.kraken.model.video.Video;

/**
 * Implementation of the Twitch Kraken Channel Videos API.
 * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-videos
 */
@Log4j2
public class ChannelVideosAPI extends AbstractPaginatedAPI<Video, ChannelVideos> {

    private ChannelsEndpoint channelsEndpoint;

    /**
     * Construct the Kraken Channel Videos API with a reference to the Kraken API instance.
     * @param kraken Kraken API instance to be used for the Users Videos API.
     */
    public ChannelVideosAPI(final Kraken kraken) {
        super(kraken);
        this.channelsEndpoint = kraken.getChannelsAPI().getChannelsEndpoint();
    }

    /**
     * Gets a list of videos for a channel, up to the maximum specified in {@code limit}.
     * @param channelId ID of the channel.
     * @param limit Maximum number of results to return.
     * @return List of videos for a channel, up to the maximum specified in {@code limit}.
     */
    public List<Video> getVideos(final long channelId, final Integer limit) {
        return performPagination(getCallParams(channelId), limit);
    }

    /**
     * {@inheritDoc}
     */
    protected Optional<ChannelVideos> innerCall(final Map<String, Object> callParams, final int apiLimit, final int offset) {
        try {
            final Long channelId = (Long) callParams.get("channelId");
            final Call<ChannelVideos> call = channelsEndpoint.getChannelVideos(channelId, apiLimit, offset);
            return executePaginatedCall(call);
        } catch (final ClassCastException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, int)}.
     */
    private Map<String, Object> getCallParams(final Long channelId) {
        return Collections.singletonMap("channelId", channelId);
    }
}
