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
import gg.sep.twitchapi.kraken.model.channel.ChannelFollows;
import gg.sep.twitchapi.kraken.model.channel.Follower;

/**
 * Implementation of the Twitch Kraken Channels Followers API.
 * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-followers
 */
@Log4j2
public class ChannelFollowersAPI extends AbstractPaginatedAPI<Follower, ChannelFollows> {

    private ChannelsEndpoint channelsEndpoint;

    /**
     * Construct the Kraken Channel Followers API with a reference to the Kraken API instance.
     * @param kraken Kraken API instance to be used for the Users Followers API.
     */
    public ChannelFollowersAPI(final Kraken kraken) {
        super(kraken);
        this.channelsEndpoint = kraken.getChannelsAPI().getChannelsEndpoint();
    }

    /**
     * Gets a list of followers of the channel ID, up to the maximum specified in {@code limit}.
     * @param channelId ID of the channel.
     * @param limit Maximum number of results to return.
     * @return List of followers of the channel ID, up the maximum specified in {@code limit}.
     */
    public List<Follower> getFollowers(final long channelId, final int limit) {
        return performPagination(getCallParams(channelId), limit);
    }

    /**
     * Gets the total follower count of the specified channel ID.
     * @param channelId IF of the channel.
     * @return Total follower count of the specified channel ID.
     */
    public long getTotalFollowers(final long channelId) {
        final Call<ChannelFollows> call = channelsEndpoint.getChannelFollowers(channelId, 1, 1);
        final Optional<ChannelFollows> followers = executePaginatedCall(call);
        return followers.map(ChannelFollows::getTotal).orElse(0L);
    }

    /**
     * {@inheritDoc}
     */
    protected Optional<ChannelFollows> innerCall(final Map<String, Object> callParams, final int apiLimit, final int offset) {
        try {
            final Long channelId = (Long) callParams.get("channelId");
            final Call<ChannelFollows> call = channelsEndpoint.getChannelFollowers(channelId, apiLimit, offset);
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
