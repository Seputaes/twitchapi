package gg.sep.twitchapi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import gg.sep.twitchapi.streams.TwitchStreamsAPI;
import gg.sep.twitchapi.users.TwitchUsersAPI;
import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

/**
 * Provides access to the various Twitch API endpoints.
 */
@Log4j2
public class TwitchAPI {

    @Getter(value = AccessLevel.PRIVATE)
    private final TwitchAPIConfig apiConfig;

    @Getter(value = AccessLevel.PRIVATE)
    private final TwitchAPIRateLimiter rateLimiter;

    @Getter(lazy = true)
    private final TwitchUsersAPI usersAPI = initUsersAPI();

    @Getter(lazy =  true)
    private final TwitchStreamsAPI streamsAPI = initStreamsAPI();

    /**
     * Construct the Twitch API using the specified API configuration.
     * @param apiConfig Configuration of the Twitch API.
     */
    public TwitchAPI(final TwitchAPIConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.rateLimiter = new TwitchAPIRateLimiter(apiConfig);
    }

    private TwitchUsersAPI initUsersAPI() {
        return new TwitchUsersAPI(getApiConfig(), getRateLimiter());
    }

    private TwitchStreamsAPI initStreamsAPI() {
        return new TwitchStreamsAPI(getApiConfig(), getRateLimiter());
    }
}
