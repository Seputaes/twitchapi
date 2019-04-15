package gg.sep.twitchapi.helix.api.users;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.api.AbstractDataListAPI;
import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.endpoint.UsersEndpoint;
import gg.sep.twitchapi.helix.model.user.User;
import gg.sep.twitchapi.helix.model.user.Users;

/**
 * Implementation of the Twitch Helix Users API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users
 *
 * TODO: Implement batching for when the number of query parameters > 100
 */
@Log4j2
public class UsersAPI extends AbstractDataListAPI<User, Users> {

    @Getter
    private UsersEndpoint usersEndpoint;

    @Getter(lazy = true)
    private final UserFollowsAPI followsAPI = initFollowsAPI();

    /**
     * Construct the Helix Users API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Users API.
     */
    public UsersAPI(final Helix helix) {
        super(helix);
        this.usersEndpoint = helix.getRetrofit().create(UsersEndpoint.class);
    }

    /**
     * Get a list of Users for the specified User IDs.
     * @param userIds List of User IDs.
     * @return List of users. IDs that are not found will not be included in the list.
     */
    public List<User> getUsersById(final List<String> userIds) {
        return getUsers(userIds, Collections.emptyList());
    }

    /**
     * Get a list of Users for the specified User Logins.
     * @param userLogins List of User Logins.
     * @return List of users. Logins that are not found will not be included in the list.
     */
    public List<User> getUsersByLogin(final List<String> userLogins) {
        return getUsers(Collections.emptyList(), userLogins);
    }

    /**
     * Gets a specific Twitch User by their ID.
     * @param userId ID of the user.
     * @return Optional of the user if it is found, empty otherwise.
     */
    public Optional<User> getUserById(final String userId) {
        final List<User> users = getUsersById(Collections.singletonList(userId));
        return (users.isEmpty()) ? Optional.empty() : Optional.of(users.get(0));
    }

    /**
     * Gets a specific Twitch User by their Login.
     * @param userLogin Login of the user.
     * @return Optional of the user if it is found, empty otherwise.
     */
    public Optional<User> getUserByLogin(final String userLogin) {
        final List<User> users = getUsersByLogin(Collections.singletonList(userLogin));
        return (users.isEmpty()) ? Optional.empty() : Optional.of(users.get(0));
    }

    /**
     * Raw access to the Get Users API and all of its supported parameters.
     * @param userIds List of User IDs.
     * @param userLogins List of User Logins
     * @return List of Users patching the IDs and Logins.
     */
    public List<User> getUsers(final List<String> userIds, final List<String> userLogins) {
        final Call<Users> call = usersEndpoint.getUsers(userIds, userLogins);
        final Optional<Users> users = executeDataListCall(call);
        return users.map(Users::getData).orElse(Collections.emptyList());
    }

    private UserFollowsAPI initFollowsAPI() {
        return new UserFollowsAPI(this.getHelix());
    }
}
