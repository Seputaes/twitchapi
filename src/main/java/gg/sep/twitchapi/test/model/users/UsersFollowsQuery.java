package gg.sep.twitchapi.test.model.users;

import gg.sep.twitchapi.test.users.TwitchUsersFollowsAPI;

/**
 * Enum for the valid Query String fields for the {@link TwitchUsersFollowsAPI}.
 */
public enum UsersFollowsQuery {
    FROM_ID("from_id"),
    TO_ID("to_id");

    private String value;

    UsersFollowsQuery(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
