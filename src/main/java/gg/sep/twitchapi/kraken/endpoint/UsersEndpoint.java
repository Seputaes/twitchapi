package gg.sep.twitchapi.kraken.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import gg.sep.twitchapi.kraken.model.user.User;

/**
 * Retrofit interface for the Kraken Users API.
 */
public interface UsersEndpoint {

    /**
     * Retrofit API call for the getUserByID endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/v5/reference/users/#get-user-by-id
     *
     * @param userId ID of the user.
     * @return getUserByID Retrofit call.
     */
    @GET("users/{user_id}")
    Call<User> getUserByID(@Path("user_id") String userId);
}
