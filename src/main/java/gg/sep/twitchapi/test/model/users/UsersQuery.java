package gg.sep.twitchapi.test.model.users;

import gg.sep.twitchapi.test.users.TwitchUsersAPI;

/**
 * Options for thr the Query String input to be used on the {@link TwitchUsersAPI}.
 */
public enum UsersQuery {
    LOGIN("login"),
    ID("id");

    private String value;

    UsersQuery(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
