package gg.sep.twitchapi.test;

import gg.sep.twitchapi.test.model.APIObject;
import gg.sep.twitchapi.test.model.DataList;
import gg.sep.twitchapi.test.utils.TwitchAPIRateLimiter;

/**
 * Abstract class for working with API endpoints which return a Data collection parameter.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
public abstract class DataAPI<T extends APIObject> extends BaseTwitchAPI {

    private final String apiPath;

    protected DataAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter, final String apiPath) {
        super(apiConfig, rateLimiter);
        this.apiPath = apiPath;
    }

    protected String getAPIPath() {
        return apiPath;
    }

    protected abstract DataList<T> getDataList(String json);
}
