package gg.sep.twitchapi.helix.model.user;

import gg.sep.twitchapi.helix.model.AbstractPaginatedTotal;

/**
 * Implements the User Follows API responses, which are data list responses containing {@link Follow} objects.
 * https://dev.twitch.tv/docs/api/reference/#get-users-follows
 */
public class UserFollows extends AbstractPaginatedTotal<Follow> {
}
