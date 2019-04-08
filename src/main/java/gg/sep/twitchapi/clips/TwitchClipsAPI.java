package gg.sep.twitchapi.clips;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import gg.sep.twitchapi.PaginatedDataAPI;
import gg.sep.twitchapi.TwitchAPIConfig;
import gg.sep.twitchapi.model.DataListPaginated;
import gg.sep.twitchapi.model.clips.ClipsQuery;
import gg.sep.twitchapi.model.clips.TwitchClip;
import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

/**
 * Implementation for the Clips API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-clips
 */
public class TwitchClipsAPI extends PaginatedDataAPI<TwitchClip> {

    private static final String API_PATH = "/clips";

    /**
     * Construct the Clips API using the specified API configuration, global Twitch rate limiter,
     * and API path.
     * @param apiConfig Configuration for the Twitch API
     * @param rateLimiter Global Rate limiter for executing the on the Twitch API.
     */
    public TwitchClipsAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter) {
        super(apiConfig, rateLimiter, API_PATH);
    }

    /**
     * Get clips for the specified User ID.
     * Returns a maximum of 1000 clips (per Twitch API limits)
     * @param userId Numeric String ID of the user for which to get clips.
     * @return Optional list of Twitch clips, empty if no clips or API error.
     */
    public Optional<List<TwitchClip>> getClipsForUserId(final String userId) {
        final List<NameValuePair> params = ImmutableList.of(
            new BasicNameValuePair(ClipsQuery.BROADCASTER_ID.toString(), userId));

        return performPagination(params, Double.POSITIVE_INFINITY);
    }

    /**
     * Gets a specific Twitch Clip by its unique ID.
     * @param clipId ID of the clip to get.
     * @return Optional of the TwitchClip if it was found, empty if no clip found or API error.
     */
    public Optional<TwitchClip> getClipById(final String clipId) {
        final List<NameValuePair> params = ImmutableList.of(
            new BasicNameValuePair(ClipsQuery.ID.toString(), clipId));

        return performPagination(params, 1).map(c -> c.iterator().next());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataListPaginated<TwitchClip> getDataList(final String json) {
        return (DataListPaginated<TwitchClip>) TwitchClip.parseDataList(json);

    }
}
