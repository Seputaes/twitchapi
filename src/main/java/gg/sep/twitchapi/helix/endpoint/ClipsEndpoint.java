package gg.sep.twitchapi.helix.endpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import gg.sep.twitchapi.helix.model.clip.Clips;

/**
 * Retrofit interface for the Helix Clips API.
 */
public interface ClipsEndpoint {
    /**
     * Retrofit API call for the getClips endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-clips
     *
     * @param ids List of clip IDs.
     * @param broadcasterId Broadcaster ID of the clip.
     * @param gameId Game ID of the clip.
     * @param endedAt RFC3339-formatted date-time string for upper bound on clip date.
     * @param startedAt RFC3339-formatted date-time string for lower bound on clip date.
     * @param first Number of items to return per paginated API call (max 100, default 20).
     * @param cursor Cursor for use between subsequent paginated API calls.
     * @return getClips Retrofit call.
     */
    @GET("clips")
    Call<Clips> getClips(
        @Query("id") List<String> ids,
        @Query("broadcaster_id") String broadcasterId,
        @Query("game_id") String gameId,
        @Query("ended_at") String endedAt,
        @Query("started_at") String startedAt,
        @Query("first") int first,
        @Query("after") String cursor
    );
}
