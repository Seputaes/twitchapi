package gg.sep.twitchapi.kraken.interceptor;

import redis.clients.jedis.JedisPool;

import gg.sep.twitchapi.TwitchAPIConfig;
import gg.sep.twitchapi.interceptor.AbstractAuthInterceptor;

/**
 * Retrofit/OkHttp3 request interceptor for applying Helix API authentication headers.
 */
public class KrakenAuthInterceptor extends AbstractAuthInterceptor {
    private static final String TOKEN_PREFIX = "OAuth";

    /**
     * Construct the auth interceptor using the specified Jedis Pool for auth caching.
     * @param jedisPool JedisPool which contains the oauth keys.
     * @param config Twitch API configuration.
     */
    public KrakenAuthInterceptor(final JedisPool jedisPool, final TwitchAPIConfig config) {
        super(jedisPool, config);
    }

    protected String getTokenPrefix() {
        return TOKEN_PREFIX;
    }
}
