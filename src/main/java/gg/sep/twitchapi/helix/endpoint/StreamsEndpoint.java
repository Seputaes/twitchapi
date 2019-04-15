package gg.sep.twitchapi.helix.endpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import gg.sep.twitchapi.helix.model.stream.Streams;
import gg.sep.twitchapi.helix.model.tag.Tags;

/**
 * Retrofit interface for the Helix Streams API.
 */
public interface StreamsEndpoint {

    /**
     * Retrofit API call for the getStreams endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-streams
     *
     * @param userIds List of User IDs
     * @param userLogins List of User Logins
     * @param gameId Game ID of the stream.
     * @param language Language of the stream.
     * @param communityIds List of community IDs for the streams.
     * @param first Number of items to return per paginated API call (max 100, default 20).
     * @param cursor Cursor for use between subsequent paginated API calls.
     * @return getStreams Retrofit call.
     */
    @GET("streams")
    Call<Streams> getStreams(
        @Query("user_id") List<String> userIds,
        @Query("user_login") List<String> userLogins,
        @Query("game_id") String gameId,
        @Query("language") String language,
        @Query("community_id") List<String> communityIds,
        @Query("first") int first,
        @Query("after") String cursor
    );

    /**
     * Retrofit API call for the getStreamTags endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-stream-tags
     * @param broadcasterId ID of the stream/broadcaster.
     * @return getStreamTags Retrofit call.
     */
    @GET("streams/tags")
    Call<Tags> getStreamTags(@Query("broadcaster_id") String broadcasterId);
}
