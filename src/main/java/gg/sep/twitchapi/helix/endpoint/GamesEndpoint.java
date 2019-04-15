package gg.sep.twitchapi.helix.endpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import gg.sep.twitchapi.helix.model.game.Games;
import gg.sep.twitchapi.helix.model.game.TopGames;

/**
 * Retrofit interface for the Helix Games API.
 */
public interface GamesEndpoint {

    /**
     * Retrofit API call for the getGames endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-games
     *
     * @param ids List of game IDs.
     * @param names List of game names.
     * @return getGames Retrofit call.
     */
    @GET("games")
    Call<Games> getGames(@Query("id") List<String> ids, @Query("name") List<String> names);

    /**
     * Retrofit API call for the getTopGames endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-top-games
     *
     * @param first Number of items to return per paginated API call (max 100, default 20).
     * @param cursor Cursor for use between subsequent paginated API calls.
     * @return getTopStreams Retrofit call.
     */
    @GET("games/top")
    Call<TopGames> getTopGames(@Query("first") int first, @Query("after") String cursor);
}
