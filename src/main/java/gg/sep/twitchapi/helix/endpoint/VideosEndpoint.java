package gg.sep.twitchapi.helix.endpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import gg.sep.twitchapi.helix.model.video.Videos;

/**
 * Retrofit interface for the Helix Videos API.
 */
public interface VideosEndpoint {
    /**
     * Retrofit API call for the getVideos endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-videos
     *
     * @param ids List of video IDs.
     * @param userId List of User IDs who own the video.
     * @param gameId Game ID of the video.
     * @param language Language of the video.
     * @param type Type of the video.
     * @param period Period for the returned videos.
     * @param sort Sort order for the returned list.
     * @param first Number of items to return per paginated API call (max 100, default 20).
     * @param cursor Cursor for use between subsequent paginated API calls.
     * @return getVideos Retrofit call.
     */
    @SuppressWarnings("ParameterNumber")
    @GET("videos")
    Call<Videos> getVideos(
        @Query("id") List<String> ids,
        @Query("user_id") String userId,
        @Query("game_id") String gameId,
        @Query("language") String language,
        @Query("type") String type,
        @Query("period") String period,
        @Query("sort") String sort,
        @Query("first") int first,
        @Query("after") String cursor
    );
}
