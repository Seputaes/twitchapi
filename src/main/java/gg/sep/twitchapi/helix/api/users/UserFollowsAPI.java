package gg.sep.twitchapi.helix.api.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.api.AbstractPaginatedTotalAPI;
import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.endpoint.UsersEndpoint;
import gg.sep.twitchapi.helix.model.user.Follow;
import gg.sep.twitchapi.helix.model.user.UserFollows;

/**
 * Implementation of the Twitch Helix User Follows API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users-follows
 *
 * TODO: Implement batching for when the number of query parameters > 100
 */
@Log4j2
public class UserFollowsAPI extends AbstractPaginatedTotalAPI<Follow, UserFollows> {

    private UsersEndpoint usersEndpoint;

    /**
     * Construct the Helix User Follows API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the User Follows API.
     */
    UserFollowsAPI(final Helix helix) {
        super(helix);
        this.usersEndpoint = helix.getUsersAPI().getUsersEndpoint();
    }

    /**
     * Gets a list of all follows to the specified User ID.
     * @param userId ID of the user for which to get followers.
     * @return List of all follows to the specified User ID.
     */
    public List<Follow> getFollowers(final String userId) {
        return getFollows(null, userId, 0);
    }

    /**
     * Gets a list of follows to the specified User ID, up to the maximum requested in {@code limit}.
     * @param userId ID of the user for which to get followers.
     * @param limit Maximum number of results to return.
     * @return List of follows to the specified User ID, up to the maximum requested in {@code limit}.
     */
    public List<Follow> getFollowers(final String userId, final int limit) {
        return getFollows(null, userId, (long) limit);
    }

    /**
     * Get a list of all the Follows being followed by the specified Twitch User ID.
     * @param userId ID of the user for which to get the users they are following.
     * @return List of all the Follows from the specified User ID.
     */
    public List<Follow> getFollowing(final String userId) {
        return getFollows(userId, null, 0);
    }

    /**
     * Get a list of the Follows being followed by the specified user ID, up to the maximum requested in {@code limit}.
     * @param userId ID of the user for which to get the users they are following.
     * @param limit Maximum number of results to return.
     * @return List of the Follows from the specified user ID, up to the maximum requested in {@code limit}.
     */
    public List<Follow> getFollowing(final String userId, final int limit) {
        return getFollows(userId, null, (long) limit);
    }

    /**
     * Gets a count of the number of followers that the specified User ID has.
     * @param userId ID of the user for which to get a follower count.
     * @return Number of followers that the specified User ID has.
     */
    public long getFollowerCount(final String userId) {
        final Optional<UserFollows> follows = innerCall(getCallParams(null, userId), 1, null);
        return follows.map(UserFollows::getTotal).orElse(0L);
    }

    /**
     * Gets the number of channels that the specified user ID is following.
     * @param userId ID of the user for which to get a followee count.
     * @return Number of channels that the specified User ID is following.
     */
    public long getFollowingCount(final String userId) {
        final Optional<UserFollows> follows = innerCall(getCallParams(userId, null), 1, null);
        return follows.map(UserFollows::getTotal).orElse(0L);
    }

    /**
     * Checks if the specified {@code fromId} user ID is following the specified {@code toId} user ID.
     * @param fromId ID of the follower.
     * @param toId ID of the followee.
     * @return If the specified {@code fromId} user ID is following the specified {@code toId} user ID.
     */
    public boolean userIsFollowing(final String fromId, final String toId) {
        final List<Follow> follows = getFollows(fromId, toId, 0);
        return !follows.isEmpty();
    }

    /**
     * Provides paginated access to the Users Follows API and its supported parameters.
     *
     * If {@code fromId} and {@code toId} are both supplied, only 1 result can ever return.
     * If both are equal, 0 results will return.
     *
     * @param fromId Follower User ID, can be null.
     * @param toId Followee User ID, can be null.
     * @param limit Maximum number of results to return.
     * @return List of Follows matching the criteria.
     */
    public List<Follow> getFollows(final String fromId, final String toId, final long limit) {
        return performPagination(getCallParams(fromId, toId), (double) limit);
    }

    /**
     * {@inheritDoc}
     */
    protected Optional<UserFollows> innerCall(final Map<String, Object> callParams, final int first, final String cursor) {
        try {
            final String fromId = (String) callParams.get("fromId");
            final String toId = (String) callParams.get("toId");
            final Call<UserFollows> call = usersEndpoint.getUserFollows(fromId, toId, first, cursor);
            return executePaginatedCall(call);
        } catch (final ClassCastException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, String)}.
     */
    private Map<String, Object> getCallParams(final String fromId, final String toId) {
        final Map<String, Object> map = new HashMap<>();
        map.put("fromId", fromId);
        map.put("toId", toId);
        return map;
    }
}
