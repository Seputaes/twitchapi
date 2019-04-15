package gg.sep.twitchapi.helix.model.subscription;

import gg.sep.twitchapi.helix.model.AbstractPaginated;

/**
 * Implements the Games API responses, which are paginated responses containing {@link Subscription} objects.
 * https://dev.twitch.tv/docs/api/reference/#get-broadcaster-subscriptions
 */
public class Subscriptions extends AbstractPaginated<Subscription> {
}
