package gg.sep.twitchapi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.util.TwitchAPIRateLimiter;

/**
 * Wrapper containing both the Kraken (v5) and Helix (New) Twitch APIs.
 *
 * Kraken Reference: https://dev.twitch.tv/docs/v5/
 * Helix Reference: https://dev.twitch.tv/docs/api/reference/
 */
@Log4j2
public class TwitchAPI {

    @Getter(AccessLevel.PRIVATE)
    private final TwitchAPIConfig apiConfig;
    private final TwitchAPIRateLimiter rateLimiter; // TODO get this working with retrofit

    @Getter(lazy = true)
    private final Kraken kraken = initKraken();

    @Getter(lazy = true)
    private final Helix helix = initHelix();

    /**
     * Construct the Twitch API using the specified API configuration.
     * @param apiConfig Configuration of the Twitch API.
     */
    public TwitchAPI(final TwitchAPIConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.rateLimiter = new TwitchAPIRateLimiter(apiConfig);
    }

    /**
     * Release the Kraken!
     * @return Initialized Kraken API with the same API Client ID.
     */
    private Kraken initKraken() {
        return new Kraken(getApiConfig().getClientId(), getApiConfig().getJedisPool());
    }

    /**
     * Initialize the Twitch Helix API with the same API Client ID.
     * @return Initialized Helix API with the same API Client ID.
     */
    private Helix initHelix() {
        return new Helix(getApiConfig().getClientId(), getApiConfig().getJedisPool());
    }
}
