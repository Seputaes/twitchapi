package gg.sep.twitchapi.kraken.api.users;

import java.util.Optional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.kraken.api.KrakenAPI;
import gg.sep.twitchapi.kraken.endpoint.UsersEndpoint;
import gg.sep.twitchapi.kraken.model.user.User;

/**
 * Implementation of the Twitch Kraken Users API.
 * Reference: https://dev.twitch.tv/docs/v5/reference/users/
 */
@Log4j2
public class UsersAPI extends KrakenAPI<User> {

    @Getter
    private UsersEndpoint usersEndpoint;

    /**
     * Construct the Kraken Channels API with a reference to the Kraken API instance.
     * @param kraken Kraken API instance to be used for the Users API.
     */
    public UsersAPI(final Kraken kraken) {
        super(kraken);
        this.usersEndpoint = kraken.getRetrofit().create(UsersEndpoint.class);
    }

    /**
     * Gets a specific Twitch User by their ID.
     * @param userId ID of the user.
     * @return Optional of the channel if it is found, empty otherwise.
     */
    public Optional<User> getUser(final String userId) {
        return executeCall(usersEndpoint.getUserByID(userId));
    }
}
