package gg.sep.twitchapi.helix.endpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import gg.sep.twitchapi.helix.model.tag.Tags;

/**
 * Retrofit interface for the Helix TagsAPI.
 */
public interface TagsEndpoint {

    /**
     * Retrofit API call for the getAllStreamTags endpoint.
     *
     * Reference: https://dev.twitch.tv/docs/api/reference/#get-all-stream-tags
     *
     * @param tagIds List of Tag IDs.
     * @param first Number of items to return per paginated API call (max 100, default 20).
     * @param cursor Cursor for use between subsequent paginated API calls.
     * @return getTags Retrofit call.
     */
    @GET("tags/streams")
    Call<Tags> getTags(@Query("tag_id") List<String> tagIds, @Query("first") int first, @Query("after") String cursor);
}
