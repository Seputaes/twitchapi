package gg.sep.twitchapi.kraken.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.kraken.model.KrakenObject;
import gg.sep.twitchapi.kraken.model.Paginated;

/**
 * Abstract implementation of a Kraken endpoint which returns a paginated list of Kraken Objects of type <code>T</code>.
 * @param <T> Type of the Kraken Objects which are contained in the data field of the response.
 * @param <P> Type of the {@link Paginated} actually returned by the API,
 *            which has <code>T</code> as its type parameter.
 */
@Log4j2
public abstract class AbstractPaginatedAPI<T extends KrakenObject, P extends Paginated<T>> extends KrakenAPI<T> {

    protected AbstractPaginatedAPI(final Kraken kraken) {
        super(kraken);
    }

    /**
     * Executes the API call and converts the response body into the {@link Paginated} Kraken Object.
     *
     * If there was an error or parsing was unsuccessful, the error will be logged and
     * empty instead will be returned.
     * @param call Retrofit call to execute.
     * @return Optional of type {@code L}, which represents the {@link Paginated} object,
     *         with a list containing type {@code T}.
     */
    protected Optional<P> executePaginatedCall(final Call<P> call) {
        try {
            // don't attach Kraken here since we're already iterating overall of the items in perform Pagination
            // it's done there instead.
            return Optional.ofNullable(call.execute().body());
        } catch (final IOException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Aggregates a list of Kraken Objects from a Paginated endpoint up to the limit (maximum results) provided.
     *
     * This method is responsible for making as many sequential API calls as necessary
     * with the cursor to retrieve all requested/available results, and stop when
     * the limit (max results) is hit or there are no more results left to get.
     *
     * Each subclass of {@link AbstractPaginatedAPI} is responsible for building the API call
     * with its unique parameters, and executing it. This is done via the
     * {@link AbstractPaginatedAPI#innerCall(Map, int, int)} abstract method.
     *
     * @param callParams Map of {@link AbstractPaginatedAPI} subclass-specific parameters
     *                   necessary for the call. Since each implementation is responsible for
     *                   building and executing its own calls, these call parameters are fed
     *                   into this method and passed back to {@link AbstractPaginatedAPI#innerCall(Map, int, int)}}
     *                   unmodified.
     * @param limit Maximum number of results to return from the API.
     *              Pagination will continue until this count is hit, or there are not more results.
     * @return Aggregated list of the Kraken Objects found for the API query across all paginated requests.
     */
    protected List<T> performPagination(final Map<String, Object> callParams, final int limit) {

        final int apiLimit = (limit >= 100) ? 100 : limit;
        final List<T> results = new ArrayList<>();

        int offset = 0;

        while (results.size() < limit) {
            final Optional<P> getCall = innerCall(callParams, apiLimit, offset);

            if (!getCall.isPresent()) {
                log.error("Inner API call was an empty response.");
                return results;
            }
            final P res = getCall.get();

            // add to the aggregated results and attach Kraken to each object.
            for (final T i : res.getItems()) {
                results.add(i);
                i.setKraken(this.getKraken());
            }

            // if the number of items we got back is less than the number we requested,
            // then it's safe to assume we've reached the end of the pagination
            // if it's equal, it'll be caught at the start of the next loop
            if (res.getItems().size() < apiLimit) {
                return results;
            }

            // update the offset and loop again
            offset += apiLimit;
        }
        return results;
    }

    /**
     * Abstract method which is used in subclasses to convert the method parameters into its API call, then execute.
     *
     * @param callParams Map of {@link AbstractPaginatedAPI} subclass-specific parameters
     *                   necessary for the call. Since each implementation is responsible for
     *                   building and executing its own calls, these call parameters are fed
     *                   into {@link AbstractPaginatedAPI#performPagination(Map, int)} and passed back to
     *                   this method unmodified.
     * @param apiLimit The number of results to return in a single API call.
     * @param offset The cursor to use for the paginated API request.
     * @return The {@link Paginated} wrapper object which contains the list of items of type {@code T},
     *         as well as the Total object.
     */
    protected abstract Optional<P> innerCall(Map<String, Object> callParams, int apiLimit, int offset);
}
