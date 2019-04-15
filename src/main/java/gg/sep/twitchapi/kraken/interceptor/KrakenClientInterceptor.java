package gg.sep.twitchapi.kraken.interceptor;

import gg.sep.twitchapi.interceptor.AbstractClientInterceptor;

/**
 * Retrofit/OkHttp3 request interceptor for applying global Kraken request properties.
 *
 * Things added such as:
 *   - Client-ID Header
 *   - Accept Header (application/vnd.twitchtv.v5+json)
 *   - Content-Type Header for JSON.
 */
public class KrakenClientInterceptor extends AbstractClientInterceptor {

    /**
     * Construct the interceptor for the specified Twitch app client ID.
     * @param clientId Client ID of the Twitch app.
     */
    public KrakenClientInterceptor(final String clientId) {
        super(clientId);
    }
}
