package gg.sep.twitchapi.helix.endpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import gg.sep.twitchapi.auth.Scope;
import gg.sep.twitchapi.auth.TokenFor;
import gg.sep.twitchapi.helix.model.subscription.Subscriptions;

/**
 * Retrofit interface for the Helix Streams API.
 */
public interface SubscriptionsEndpoint {

    /**
     * Retrofit API call for the getBroadcasterSubscriptions endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-broadcaster-subscriptions
     *
     * @param broadcasterId ID of the broadcaster.
     * @param userIds List of user IDs of the user/subscriber.
     * @param first Number of items to return per paginated API call (max 100, default 20).
     * @param cursor Cursor for use between subsequent paginated API calls.
     * @return getBroadcasterSubscriptions Retrofit call.
     */
    @Scope("channel:read:subscriptions")
    @GET("subscriptions")
    Call<Subscriptions> getBroadcasterSubscriptions(
        @Query("broadcaster_id") @TokenFor String broadcasterId,
        @Query("user_id") List<String> userIds,
        @Query("first") int first,
        @Query("after") String cursor
    );
}
