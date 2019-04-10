package gg.sep.twitchapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableSet;
import lombok.extern.log4j.Log4j2;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataListPaginated;
import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

/**
 * Variation on {@link DataAPI} which supports pagination through multiple requests for large data sets.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
@Log4j2
public abstract class PaginatedDataAPI<T extends APIObject> extends DataAPI<T> {

    private static final int MAX = 100; // the maximum allowed by the TwitchAPI per call.

    protected PaginatedDataAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter,
                               final String apiPath) {
        super(apiConfig, rateLimiter, apiPath);
    }


    /**
     * Responsible for constructing the Paginated Data List response of the specified type.
     * @param json Raw JSON string of the API's response.
     * @return Paginated Data List response of the specified subtype of {@link APIObject}.
     */
    @Override
    protected abstract DataListPaginated<T> getDataList(String json);

    /**
     * Interacts with the paginated TwitchAPIs, making repeated calls using the cursor.
     *
     * This will keep making requests to the TwitchAPI to retrieve more paginated data until
     * there is no more data, the maximum records is hit, or the cursor is null.
     * @param params GET request parameters to add to the request. will be appended with the necessary params
     *               for pagination.
     * @param max Maximum number of data entry records to return.
     * @return List of data records. Will return Optional empty if there was an error, otherwise a list.
     */
    protected List<T> performPagination(final List<NameValuePair> params, final double max) {
        final List<T> dataList = new ArrayList<>();

        String cursor = null;
        boolean first = true;
        Optional<String> jsonResponse;

        // continue getting Paginated JSON responses if this is the first request,
        // until the datalist size is >= the maximum, or the there is no longer a cursor/data in the response.
        while (first || ((cursor != null) && dataList.size() < max)) {
            first = false;
            jsonResponse = getPaginatedJson(params, cursor);
            // if something went wrong, break out of the loop and return what we have
            // check the logs for errors
            if (jsonResponse.isEmpty()) {
                log.error("Something went wrong getting the JSON response. Check logs for details");
                break;
            }

            final DataListPaginated<T> response = getDataList(jsonResponse.get());
            cursor = response.getPagination().getCursor();
            // Append the new data to the list.
            if (!response.getData().isEmpty()) {
                dataList.addAll(response.getData());
            } else {
                break;
            }
        }
        return dataList;
    }

    /**
     * Assigns the necessary query parameters to the request for pagination and gets a single JSON string response.
     * @param params Request GET query parameters.
     * @param cursor Pagination cursor string, if any.
     * @return Raw JSON string from the API.
     */
    private Optional<String> getPaginatedJson(final List<NameValuePair> params, final String cursor) {
        final List<NameValuePair> paramsList = new ArrayList<>(params);
        // remove first/after from the list of parameters if it exists
        IntStream.range(0, paramsList.size())
            .filter(i -> ImmutableSet.of("first", "after").contains(params.get(i).getName()))
            .boxed()
            .forEach(i -> paramsList.remove(i));

        paramsList.add(new BasicNameValuePair("first", String.valueOf(MAX)));

        if (cursor != null && cursor.length() > 0) {
            paramsList.add(new BasicNameValuePair("after", cursor));
        }

        return getJsonResponse(paramsList);
    }
}
