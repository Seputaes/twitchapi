package gg.sep.twitchapi.model.users;

/**
 * Options for thr the Query String input to be used on the {@link gg.sep.twitchapi.users.TwitchUsersAPI}.
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
