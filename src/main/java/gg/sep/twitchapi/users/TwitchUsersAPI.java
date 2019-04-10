package gg.sep.twitchapi.users;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import org.apache.http.message.BasicNameValuePair;

import gg.sep.twitchapi.DataAPI;
import gg.sep.twitchapi.TwitchAPIConfig;
import gg.sep.twitchapi.model.DataList;
import gg.sep.twitchapi.model.users.TwitchUser;
import gg.sep.twitchapi.model.users.TwitchUsersFollows;
import gg.sep.twitchapi.model.users.UsersQuery;
import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

/**
 * Implementation for the Users API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users
 */
public class TwitchUsersAPI extends DataAPI<TwitchUser> {

    private static final String API_PATH = "/users";

    @Getter(lazy = true)
    private final TwitchUsersFollowsAPI followersAPI = initFollowsAPI();

    /**
     * Construct the Users API using the specified API configuration, global Twitch rate limiter,
     * and API path.
     * @param apiConfig Configuration for the Twitch API
     * @param rateLimiter Global Rate Limiter for executing on the Twitch API.
     */
    public TwitchUsersAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter) {
        super(apiConfig, rateLimiter, API_PATH);
    }

    /**
     * Initialize the Follows API that's "part of" the Users API.
     * This API isn't fully featured enough to warrant its own "root" object, so it's hidden within the Users API.
     * @return Cached instance of the Users Follows API
     */
    private TwitchUsersFollowsAPI initFollowsAPI() {
        return new TwitchUsersFollowsAPI(getApiConfig(), getRateLimiter());
    }

    /**
     * Gets a single Twitch User by the specified login.
     * @param login Twitch User's login (not necessarily their Display Name)
     * @return Optional of a single TwitchUser. Empty if not found or other API error.
     */
    public Optional<TwitchUser> getUserByLogin(final String login) {
        return queryUsers(UsersQuery.LOGIN, login);
    }

    /**
     * Gets a single Twitch User by the specified User ID (numeric string).
     * @param id Numeric string of the Twitch User.
     * @return Optional of a single Twitch User. Empty if not found or other API error.
     */
    public Optional<TwitchUser> getUserByID(final String id) {
        return queryUsers(UsersQuery.ID, id);
    }

    /**
     * Returns <code>true</code> if the followerId is following the followeeId on Twitch.
     * @param followerId Twitch User ID of the follower.
     * @param followeeId Twitch User ID of the followee.
     * @return <code>true</code> if the followerId is following the followeeId on Twitch;
     *         <code>false</code> otherwise.
     */
    public boolean userIsFollowing(final String followerId, final String followeeId) {
        return getFollowersAPI().userIsFollowing(followerId, followeeId);
    }

    /**
     * Gets a list of ALL of the TwitchUserFollows that a specified Twitch User is following.
     * WARNING: This list could be very large and this method can block until all are returned.
     *          Twitch places a hard response limit of 100 objects per response.
     * @param userId Numeric string of the Twitch User.
     * @return Optional of a list of TwitchUsersFollows. Empty optional if there was an API error.
     */
    public List<TwitchUsersFollows> getUserFollowing(final String userId) {
        return getFollowersAPI().getUserFollowing(userId);
    }

    /**
     * Gets a list of the maximum number of the TwitchUserFollows that a specified Twitch User is following.
     * WARNING: This list could be very large and this method can block until all are returned.
     *          Twitch places a hard response limit of 100 objects per response.
     * @param userId Numeric string of the Twitch User.
     * @param max Maximum number of objects to return in the list (rounded up to the nearest 100).
     * @return Optional of a list of TwitchUsersFollows. Empty optional if there was an API error.
     */
    public List<TwitchUsersFollows> getUserFollowing(final String userId, final int max) {
        return getFollowersAPI().getUserFollowing(userId, (double) max);
    }

    /**
     * Gets a list of ALL of the TwitchUserFollows that are following the specified Twitch User ID.
     * WARNING: This list could be very large and this method can block until all are returned.
     *          Twitch places a hard response limit of 100 objects per response.
     * @param userId Numeric string of the Twitch User for which to get followers.
     * @return Optional of a list of TwitchUsersFollows. Empty optional if there was an API error.
     */
    public List<TwitchUsersFollows> getUserFollowers(final String userId) {
        return getFollowersAPI().getUserFollowers(userId);
    }

    /**
     * Gets a list of the maximum of the TwitchUserFollows that are following the specified Twitch User ID.
     * WARNING: This list could be very large and this method can block until all are returned.
     *          Twitch places a hard response limit of 100 objects per response.
     * @param userId Numeric string of the Twitch User for which to get followers.
     * @param max Maximum number of objects to return in the list (rounded up to the nearest 100)
     * @return Optional of a list of TwitchUsersFollows. Empty optional if there was an API error.
     */
    public List<TwitchUsersFollows> getUserFollowers(final String userId, final int max) {
        return getFollowersAPI().getUserFollowers(userId, (double) max);
    }

    @Override
    protected DataList<TwitchUser> getDataList(final String json) {
        return TwitchUser.parseDataList(json);
    }

    private Optional<TwitchUser> queryUsers(final UsersQuery query, final String value) {
        final Optional<String> jsonResponse = getJsonResponse(
            Collections.singletonList(new BasicNameValuePair(query.toString(), value)));

        if (jsonResponse.isEmpty()) {
            return Optional.empty();
        }

        final DataList<TwitchUser> response = getDataList(jsonResponse.get());
        return response.getData().isEmpty() ? Optional.empty() : Optional.of(response.getData().get(0));
    }
}
