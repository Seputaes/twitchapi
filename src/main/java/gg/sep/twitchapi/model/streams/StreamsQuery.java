package gg.sep.twitchapi.model.streams;

/**
 * Possible values to be used as query parameters for the {@link gg.sep.twitchapi.streams.TwitchStreamsAPI}.
 */
public enum StreamsQuery {

    COMMUNITY_ID("community_id"),
    GAME_ID("game_id"),
    LANGUAGE("language"),
    USER_ID("user_id"),
    USER_LOGIN("user_login");

    private String value;

    StreamsQuery(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
