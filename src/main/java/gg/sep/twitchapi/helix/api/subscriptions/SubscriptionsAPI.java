package gg.sep.twitchapi.helix.api.subscriptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.api.AbstractPaginatedAPI;
import gg.sep.twitchapi.helix.endpoint.SubscriptionsEndpoint;
import gg.sep.twitchapi.helix.model.subscription.Subscription;
import gg.sep.twitchapi.helix.model.subscription.Subscriptions;

/**
 * Implementation of the Twitch Helix Subscriptions API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-broadcaster-subscriptions
 *
 * TODO: Implement batching for when the number of query parameters > 100
 */
@Log4j2
public class SubscriptionsAPI extends AbstractPaginatedAPI<Subscription, Subscriptions> {

    private SubscriptionsEndpoint subscriptionsEndpoint;

    /**
     * Construct the Helix Subscriptions API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Subscriptions API.
     */
    public SubscriptionsAPI(final Helix helix) {
        super(helix);
        this.subscriptionsEndpoint = helix.getRetrofit().create(SubscriptionsEndpoint.class);
    }

    /**
     * Get a list of subscriptions for the specified broadcaster ID.
     *
     * Requires auth scope: channel:read:subscriptions
     *
     * @param broadcasterId ID of the broadcaster.
     * @return List of subscriptions for the specified broadcaster ID.
     */
    public List<Subscription> getSubscriptions(final String broadcasterId) {
        return getSubscriptions(broadcasterId, 0);
    }

    /**
     * Get a list of subscriptions for the specified broadcaster ID, up to the limit specified in {@code limit}.
     *
     * Requires auth scope: channel:read:subscriptions
     *
     * @param broadcasterId ID of the broadcaster.
     * @param limit Maximum number of results to return.
     * @return List of subscriptions for the specified broadcaster ID up to the limit specified in {@code limit}.
     */
    public List<Subscription> getSubscriptions(final String broadcasterId, final int limit) {
        return performPagination(getCallParams(broadcasterId, null), (double) limit);
    }

    /**
     * Gets a list of subscriptions for the specified broadcasterID for the specified user IDs.
     *
     * Requires auth scope: channel:read:subscriptions
     *
     * @param broadcasterId ID of the broadcaster.
     * @param userIds List of IDs of the subscribing users.
     * @return List of subscriptions for the specified broadcasterID for the specified user IDs.
     */
    public List<Subscription> getSubscriptions(final String broadcasterId, final List<String> userIds) {
        return performPagination(getCallParams(broadcasterId, userIds), 0);
    }

    /**
     * Checks if the specified user ID is subscribed to the specified broadcaster.
     *
     * Requires auth scope: channel:read:subscriptions
     *
     * @param userId ID of the user/subscriber.
     * @param broadcasterId ID of the broadcaster.
     * @return True of the user is subscribed, false otherwise.
     */
    public boolean userIsSubscribed(final String userId, final String broadcasterId) {
        final List<Subscription> subs = getSubscriptions(broadcasterId, Collections.singletonList(userId));
        return !subs.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    protected Optional<Subscriptions> innerCall(final Map<String, Object> callParams, final int first,
                                                final String cursor) {
        try {
            final String broadcasterId = (String) callParams.get("broadcasterId");
            final List<String> userIds = (List<String>) callParams.get("userIds");
            final Call<Subscriptions> call = subscriptionsEndpoint.getBroadcasterSubscriptions(broadcasterId, userIds,
                first, cursor);
            return executePaginatedCall(call);
        } catch (final ClassCastException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, String)}.
     */
    private Map<String, Object> getCallParams(final String broadcasterId, final List<String> userIds) {
        final Map<String, Object> map = new HashMap<>();
        map.put("broadcasterId", broadcasterId);
        map.put("userIds", userIds);
        return map;
    }
}
