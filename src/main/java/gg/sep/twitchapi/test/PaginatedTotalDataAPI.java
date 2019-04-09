package gg.sep.twitchapi.test;

import gg.sep.twitchapi.test.model.APIObject;
import gg.sep.twitchapi.test.model.DataListTotalPaginated;
import gg.sep.twitchapi.test.utils.TwitchAPIRateLimiter;

/**
 * Variation of {@link PaginatedDataAPI}, which also contains a total number of records matching, without pagination.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
public abstract class PaginatedTotalDataAPI<T extends APIObject> extends PaginatedDataAPI<T> {

    protected PaginatedTotalDataAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter,
                                    final String apiPath) {
        super(apiConfig, rateLimiter, apiPath);
    }

    @Override
    protected abstract DataListTotalPaginated<T> getDataList(String json);
}
