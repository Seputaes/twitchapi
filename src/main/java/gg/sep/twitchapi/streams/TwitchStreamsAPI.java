package gg.sep.twitchapi.streams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import gg.sep.twitchapi.PaginatedDataAPI;
import gg.sep.twitchapi.TwitchAPIConfig;
import gg.sep.twitchapi.model.DataListPaginated;
import gg.sep.twitchapi.model.streams.StreamsQuery;
import gg.sep.twitchapi.model.streams.TwitchStream;
import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

/**
 * Implementation for the Streams API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-streams
 */
public class TwitchStreamsAPI extends PaginatedDataAPI<TwitchStream> {

    private static final String API_PATH = "/streams";
    private static final int ENTRIES_PER_STREAM_REQ = 100;

    /**
     * Construct the Streams API using the specified API configuration, global Twitch rate limiter,
     * and API path.
     * @param apiConfig Configuration for the Twitch API
     * @param rateLimiter Global Rate Limiter for executing on the Twitch API.
     */
    public TwitchStreamsAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter) {
        super(apiConfig, rateLimiter, API_PATH);
    }

    /**
     * Get the current top 1000 streams.
     * This generally will not return exactly 1000 streams due to caching and differences between pages in pagination.
     * @return Optional of the top 1000 Twitch Streams, empty if error.
     */
    public Optional<Set<TwitchStream>> getTopStreams() {
        return performPagination(new ArrayList<>(), 1000).map(HashSet::new);
    }

    /**
     * Gets the stream objects for the specified collection of user logins.
     *
     * If more than 100 are supplied, this will make batch requests to the API in sets of 100.
     * @param userLogins Collection of user logins. This will be filtered down to a Set to ensure no duplicates.
     * @return An optional Set of unique Streams matching the user logins, Optional empty if API error.
     */
    public Optional<Set<TwitchStream>> getStreamsForUserLogins(final Collection<String> userLogins) {

        final Set<String> logins = new HashSet<>(userLogins);
        final List<NameValuePair> params = buildParams(
            logins.stream()
                .map(ul -> Pair.of(StreamsQuery.USER_LOGIN, ul))
                .collect(Collectors.toList())
        );

        final Set<TwitchStream> streams = new HashSet<>();
        final Iterable<List<NameValuePair>> batches = Iterables.partition(params, ENTRIES_PER_STREAM_REQ);

        for (final List<NameValuePair> batchParams : batches) {
            performPagination(batchParams, batchParams.size()).ifPresent(streams::addAll);
        }
        return (streams.isEmpty()) ? Optional.empty() : Optional.of(streams);
    }

    /**
     * Gets a single Twitch Stream for the specified Twitch user login.
     * @param userLogin Twitch User's login name.
     * @return Optional of the TwitchStream, empty if not found or API error.
     */
    public Optional<TwitchStream> getStreamForUserLogin(final String userLogin) {
        return getStreamsForUserLogins(ImmutableSet.of(userLogin)).map(c -> c.iterator().next());
    }

    @Override
    protected DataListPaginated<TwitchStream> getDataList(final String json) {
        return (DataListPaginated<TwitchStream>) TwitchStream.parseDataList(json);
    }

    private List<NameValuePair> buildParams(final List<Pair<StreamsQuery, String>> parameters) {
        return parameters.stream()
            .map(p -> new BasicNameValuePair(p.getLeft().toString(), p.getRight()))
            .collect(Collectors.toList());
    }
}
