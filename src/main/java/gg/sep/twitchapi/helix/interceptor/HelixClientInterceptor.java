package gg.sep.twitchapi.helix.interceptor;

import gg.sep.twitchapi.interceptor.AbstractClientInterceptor;

/**
 * Retrofit/OkHttp3 request interceptor for applying global Helix request properties.
 *
 * Things added such as:
 *   - Client-ID Header
 *   - Accept Header (application/vnd.twitchtv.v5+json)
 *   - Content-Type Header for JSON.
 */
public class HelixClientInterceptor extends AbstractClientInterceptor {

    /**
     * Construct the interceptor for the specified Twitch app client ID.
     * @param clientId Client ID of the Twitch app.
     */
    public HelixClientInterceptor(final String clientId) {
        super(clientId);
    }
}
