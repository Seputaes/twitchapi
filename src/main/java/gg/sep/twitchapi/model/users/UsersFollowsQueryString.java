package gg.sep.twitchapi.model.users;

/**
 * Enum for the valid Query String fields for the {@link gg.sep.twitchapi.users.TwitchUsersFollowsAPI}.
 */
public enum UsersFollowsQueryString {
    FROM_ID("from_id"),
    TO_ID("to_id");

    private String value;

    UsersFollowsQueryString(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
