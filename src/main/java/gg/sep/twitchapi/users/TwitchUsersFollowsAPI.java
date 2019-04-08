package gg.sep.twitchapi.users;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import gg.sep.twitchapi.PaginatedTotalDataAPI;
import gg.sep.twitchapi.TwitchAPIConfig;
import gg.sep.twitchapi.model.DataListTotalPaginated;
import gg.sep.twitchapi.model.users.TwitchUsersFollows;
import gg.sep.twitchapi.model.users.UsersFollowsQuery;
import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

/**
 * Implementation for the Users Follows API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users-follows
 */
public class TwitchUsersFollowsAPI extends PaginatedTotalDataAPI<TwitchUsersFollows> {

    private static final String API_PATH = "/users/follows";

    /**
     * Construct the Users Follows API using the specified API configuration, global Twitch rate limiter,
     * and API path.
     * @param apiConfig Configuration for the Twitch API
     * @param rateLimiter Global Rate limiter for executing the on the Twitch API.
     */
    TwitchUsersFollowsAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter) {
        super(apiConfig, rateLimiter, API_PATH);
    }

    List<TwitchUsersFollows> getUserFollowing(final String userId, final double max) {
        return baseUsersFollows(UsersFollowsQuery.FROM_ID, userId, max);
    }

    List<TwitchUsersFollows> getUserFollowing(final String userId) {
        return baseUsersFollows(UsersFollowsQuery.FROM_ID, userId, Double.POSITIVE_INFINITY);
    }

    List<TwitchUsersFollows> getUserFollowers(final String userId, final double max) {
        return baseUsersFollows(UsersFollowsQuery.TO_ID, userId, max);
    }

    List<TwitchUsersFollows> getUserFollowers(final String userId) {
        return baseUsersFollows(UsersFollowsQuery.TO_ID, userId, Double.POSITIVE_INFINITY);
    }

    private List<TwitchUsersFollows> baseUsersFollows(final UsersFollowsQuery query,
                                                                final String value,
                                                                final double max) {

        final List<NameValuePair> params = ImmutableList.of(new BasicNameValuePair(query.toString(), value));
        return performPagination(params, max);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataListTotalPaginated<TwitchUsersFollows> getDataList(final String json) {
        return (DataListTotalPaginated<TwitchUsersFollows>) TwitchUsersFollows.parseDataList(json);

    }


}
