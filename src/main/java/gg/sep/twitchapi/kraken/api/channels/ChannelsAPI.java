package gg.sep.twitchapi.kraken.api.channels;

import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.kraken.api.KrakenAPI;
import gg.sep.twitchapi.kraken.endpoint.ChannelsEndpoint;
import gg.sep.twitchapi.kraken.model.channel.Channel;
import gg.sep.twitchapi.kraken.model.channel.Follower;
import gg.sep.twitchapi.kraken.model.team.Team;
import gg.sep.twitchapi.kraken.model.video.Video;

/**
 * Implementation of the Twitch Kraken Channels API.
 * Reference: https://dev.twitch.tv/docs/v5/reference/channels/
 */
@Log4j2
public class ChannelsAPI extends KrakenAPI<Channel> {

    @Getter
    private ChannelsEndpoint channelsEndpoint;

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final ChannelFollowersAPI followersAPI = initFollowersAPI();

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final ChannelTeamsAPI teamsAPI = initTeamsAPI();

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final ChannelVideosAPI videosAPI = initVideosAPI();

    /**
     * Construct the Kraken Channels API with a reference to the Kraken API instance.
     * @param kraken Kraken API instance to be used for the Users API.
     */
    public ChannelsAPI(final Kraken kraken) {
        super(kraken);
        this.channelsEndpoint = kraken.getRetrofit().create(ChannelsEndpoint.class);
    }

    /**
     * Gets a specific Twitch Channel by its ID.
     * @param channelId ID of the channel.
     * @return Optional of the channel if it is found, empty otherwise.
     */
    public Optional<Channel> getChannel(final long channelId) {
        return executeCall(this.channelsEndpoint.getChannelByID(channelId));
    }

    /**
     * Updates a channel's Status (title) and Game to the new values on the Channel object.
     * @param channel Channel containing the new Status and/or Game.
     * @return Optional of the updated channel if it is found, empty otherwise.
     */
    public Optional<Channel> updateChannel(final Channel channel) {
        return executeCall(updateChannel(
            channel.getId(), channel.getPut()
        ));
    }

    /**
     * Gets the total follower count of the specified channel ID.
     * @param channelId ID of the channel.
     * @return Total follower count of the specified channel ID.
     */
    public long getTotalFollowers(final long channelId) {
        return getFollowersAPI().getTotalFollowers(channelId);
    }

    /**
     * Gets a list of followers of the channel ID, up to the maximum specified in {@code limit}.
     * @param channelId ID of the channel.
     * @param limit Maximum number of results to return.
     * @return List of followers of the channel ID, up the maximum specified in {@code limit}.
     */
    public List<Follower> getFollowers(final long channelId, final int limit) {
        return getFollowersAPI().getFollowers(channelId, limit);
    }

    /**
     * Gets a list of teams for a channel.
     * @param channelId ID of the channel.
     * @return List of teams for a channel.
     */
    public List<Team> getTeams(final long channelId) {
        return getTeamsAPI().getTeams(channelId);
    }

    /**
     * Gets a list of videos for a channel, up to the maximum specified in {@code limit}.
     * @param channelId ID of the channel.
     * @param limit Maximum number of results to return.
     * @return List of videos for a channel, up to the maximum specified in {@code limit}.
     */
    public List<Video> getVideos(final long channelId, final int limit) {
        return getVideosAPI().getVideos(channelId, limit);
    }

    private Call<Channel> updateChannel(final long channelId, final Channel.ChannelPut channelPut) {
        return this.channelsEndpoint.updateChannel(channelId, channelPut);
    }

    private ChannelFollowersAPI initFollowersAPI() {
        return new ChannelFollowersAPI(this.getKraken());
    }

    private ChannelTeamsAPI initTeamsAPI() {
        return new ChannelTeamsAPI(this.getKraken());
    }

    private ChannelVideosAPI initVideosAPI() {
        return new ChannelVideosAPI(this.getKraken());
    }
}
