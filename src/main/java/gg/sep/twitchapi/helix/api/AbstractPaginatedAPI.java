package gg.sep.twitchapi.helix.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.model.Paginated;
import gg.sep.twitchapi.helix.model.HelixObject;

/**
 * Abstract implementation of a Helix endpoint which returns a paginated list of Helix Objects of type {@code T}.
 * @param <T> Type of the Helix Objects which are contained in the data field of the response.
 * @param <P> Type of the {@link Paginated} actually returned by the API,
 *            which has {@code T} as its type parameter.
 */
@Log4j2
public abstract class AbstractPaginatedAPI<T extends HelixObject, P extends Paginated<T>> extends HelixAPI<T> {

    protected AbstractPaginatedAPI(final Helix helix) {
        super(helix);
    }

    /**
     * Executes the API call and converts the response body into the {@link Paginated} Helix Object.
     *
     * If there was an error or parsing was unsuccessful, the error will be logged and
     * empty instead will be returned.
     * @param call Retrofit call to execute.
     * @return Optional of type {@code L}, which represents the {@link Paginated} object,
     *         with a list containing type {@code T}.
     */
    protected Optional<P> executePaginatedCall(final Call<P> call) {
        try {
            // don't attach Helix here since we're already iterating overall of the items in perform Pagination
            // it's done there instead.
            return Optional.ofNullable(call.execute().body());
        } catch (final IOException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Aggregates a list of Helix Objects from a Paginated endpoint up to the limit (maximum results) provided.
     *
     * This method is responsible for making as many sequential API calls as necessary
     * with the cursor to retrieve all requested/available results, and stop when
     * the limit (max results) is hit or there are no more results left to get.
     *
     * Each subclass of {@link AbstractPaginatedAPI} is responsible for building the API call
     * with its unique parameters, and executing it. This is done via the
     * {@link AbstractPaginatedAPI#innerCall(Map, int, String)} abstract method.
     *
     * @param callParams Map of {@link AbstractPaginatedAPI} subclass-specific parameters
     *                   necessary for the call. Since each implementation is responsible for
     *                   building and executing its own calls, these call parameters are fed
     *                   into this method and passed back to {@link AbstractPaginatedAPI#innerCall(Map, int, String)}}
     *                   unmodified.
     * @param limit Maximum number of results to return from the API.
     *              Pagination will continue until this count is hit, or there are not more results.
     * @return Aggregated list of the Helix Objects found for the API query across all paginated requests.
     */
    protected List<T> performPagination(final Map<String, Object> callParams, final double limit) {
        final double maxResults = (limit <= 0) ? Double.POSITIVE_INFINITY : limit;

        int first = (maxResults >= 100) ? 100 : (int) maxResults;
        final List<T> results = new ArrayList<>();

        double remaining = maxResults;
        String cursor = null; // first request won't include a cursor

        while (results.size() < maxResults) {
            // Reduce the remaining maximum results by how many we got in this request
            // this allows us to only request the exact number we need
            // Iterations eg:
            //     1. limit(215), first(100), results(0 -> 100)
            //     2. limit(115), first(100), results(100 -> 200)
            //     3. limit(15), first(100), results(200 -> 215)
            //         - exits the loop here since the number we got back is < the number we requested
            if ((remaining - first) < 0) {
                first = (int) remaining;
            } else {
                remaining -= first;
            }

            // call int the inner object for it to construct its own API call.
            final Optional<P> getCall = innerCall(callParams, first, cursor);
            if (!getCall.isPresent()) {
                log.error("Inner API call was an empty response");
                return results;
            }
            final P res = getCall.get();

            // add to the aggregated results and attach Helix to each object.
            for (final T d : res.getData()) {
                results.add(d);
                d.setHelix(this.getHelix());
            }
            // update the cursor for the next loop
            cursor = res.getPagination().getCursor();
            // if the number of results we got back is < the number we requested (there's no more left)
            // or if the cursor is null (no way to paginate), return the results we have
            // this is also our exit if limit is "Infinity"
            if (res.getData().size() < first || cursor == null) {
                return results;
            }
        }
        return results;
    }

    /**
     * Abstract method which is used in subclasses to convert the method parameters into its API call, then execute.
     *
     * @param callParams Map of {@link AbstractPaginatedAPI} subclass-specific parameters
     *                   necessary for the call. Since each implementation is responsible for
     *                   building and executing its own calls, these call parameters are fed
     *                   into {@link AbstractPaginatedAPI#performPagination(Map, double)} and passed back to
     *                   this method unmodified.
     * @param first The number of results to return in a single API call.
     * @param cursor The cursor to use for the paginated API request.
     * @return The {@link Paginated} wrapper object which contains the list of items of type {@code T},
     *         as well as the {@link gg.sep.twitchapi.helix.model.Pagination} object.
     */
    protected abstract Optional<P> innerCall(Map<String, Object> callParams, int first, String cursor);
}
