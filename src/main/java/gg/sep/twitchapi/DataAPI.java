package gg.sep.twitchapi;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataListResponse;
import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

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

    protected abstract DataListResponse<T> getDataList(String json);
}
