package gg.sep.twitchapi;

import lombok.Builder;
import lombok.Getter;

/**
 * Builder/Model for the Twitch API Configuration.
 * Contains all of the fields necessary for accurately initializing the API class.
 */
@Getter
@Builder
public class TwitchAPIConfig {
    private String login;
    private String clientId;
    private String oauthToken;
    private double apiRateLimit;
}
