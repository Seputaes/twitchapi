package gg.sep.twitchapi.kraken.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import gg.sep.twitchapi.auth.Scope;
import gg.sep.twitchapi.auth.TokenFor;
import gg.sep.twitchapi.kraken.model.channel.Channel;
import gg.sep.twitchapi.kraken.model.channel.ChannelFollows;
import gg.sep.twitchapi.kraken.model.channel.ChannelTeams;
import gg.sep.twitchapi.kraken.model.channel.ChannelVideos;

/**
 * Retrofit interface for the Kraken Channels API.
 * TODO: Implement the endpoint for getting a channel by OAuth token.
 */
public interface ChannelsEndpoint {

    /**
     * Retrofit API call for the getChannelByID endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-by-id
     *
     * @param channelId ID of the channel.
     * @return getChannelByID Retrofit call.
     */
    @GET("channels/{channel_id}")
    Call<Channel> getChannelByID(@Path("channel_id")long channelId);

    /**
     * Retrofit API call for the updateChannel endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#update-channel
     *
     * @param channelId ID of the channel.
     * @param channel Channel PUT object to serialize.
     * @return updateChannel Retrofit Call.
     */
    @Scope("channel_editor")
    @PUT("channels/{channel_id}")
    Call<Channel> updateChannel(@Path("channel_id") @TokenFor long channelId, @Body Channel.ChannelPut channel);

    /**
     * Retrofit API call for the getChannelFollowers endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-followers
     *
     * @param channelId ID of the channel.
     * @param limit Number of items to return per paginated API call (max 100, default 25).
     * @param offset Starting point for the returned paginated objects.
     * @return getChannelFollowers Retrofit call.
     */
    @GET("channels/{channel_id}/follows")
    Call<ChannelFollows> getChannelFollowers(@Path("channel_id") long channelId, @Query("limit") int limit, @Query("offset") Integer offset);

    /**
     * Retrofit API call for the getChannelVideos endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-videos
     *
     * @param channelId ID of the channel.
     * @param limit Number of items to return per paginated API call (max 100, default 25).
     * @param offset Starting point for the returned paginated objects.
     * @return getChannelVideos Retrofit call.
     */
    @GET("channels/{channel_id}/videos")
    Call<ChannelVideos> getChannelVideos(@Path("channel_id") long channelId, @Query("limit") int limit, @Query("offset") Integer offset);

    /**
     * Retrofit API call for the getChannelTeams endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-teams
     *
     * @param channelId ID of the channel.
     * @return getChannelTeams Retrofit call.
     */
    @GET("channels/{channel_id}/teams")
    Call<ChannelTeams> getChannelTeams(@Path("channel_id") long channelId);
}
