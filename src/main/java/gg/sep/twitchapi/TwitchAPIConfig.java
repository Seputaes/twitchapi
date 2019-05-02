package gg.sep.twitchapi;

import lombok.Builder;
import lombok.Getter;
import redis.clients.jedis.JedisPool;

/**
 * Builder/Model for the Twitch API Configuration.
 * Contains all of the fields necessary for accurately initializing the API class.
 */
@Getter
@Builder
public class TwitchAPIConfig {
    private String login;
    private String clientId;
    private String appOauthToken;
    private JedisPool jedisPool;
    private double apiRateLimit;
}
