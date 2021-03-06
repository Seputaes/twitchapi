package gg.sep.twitchapi.helix.interceptor;

import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.JedisPool;

import gg.sep.twitchapi.TwitchAPIConfig;
import gg.sep.twitchapi.interceptor.AbstractAuthInterceptor;

/**
 * Retrofit/OkHttp3 request interceptor for applying Helix API authentication headers.
 */
@Log4j2
public class HelixAuthInterceptor extends AbstractAuthInterceptor {
    private static final String TOKEN_PREFIX = "Bearer";

    /**
     * Construct the auth interceptor using the specified Jedis Pool for auth caching.
     * @param jedisPool JedisPool which contains the oauth keys.
     * @param config Twitch API configuration.
     */
    public HelixAuthInterceptor(final JedisPool jedisPool, final TwitchAPIConfig config) {
        super(jedisPool, config);
    }

    protected String getTokenPrefix() {
        return TOKEN_PREFIX;
    }
}
