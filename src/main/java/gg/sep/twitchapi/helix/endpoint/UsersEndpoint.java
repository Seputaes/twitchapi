package gg.sep.twitchapi.helix.endpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import gg.sep.twitchapi.helix.model.user.UserFollows;
import gg.sep.twitchapi.helix.model.user.Users;

/**
 * Retrofit interface for the Helix Users API.
 */
public interface UsersEndpoint {

    /**
     * Retrofit API call for the getUsers endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-users
     *
     * @param ids List of user IDs.
     * @param logins List of user logins.
     * @return getUsers Retrofit call.
     */
    @GET("users")
    Call<Users> getUsers(@Query("id") List<String> ids, @Query("login") List<String> logins);

    /**
     * Retrofit API call for the getUserFollows endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-users-follows
     *
     * @param fromId User ID of the follower.
     * @param toId User ID of the followee.
     * @param first Number of items to return per paginated API call (max 100, default 20).
     * @param cursor Cursor for use between subsequent paginated API calls.
     * @return getUserFollows Retrofit call.
     */
    @GET("users/follows")
    Call<UserFollows> getUserFollows(
        @Query("from_id") String fromId,
        @Query("to_id") String toId,
        @Query("first") int first,
        @Query("after") String cursor
    );
}
