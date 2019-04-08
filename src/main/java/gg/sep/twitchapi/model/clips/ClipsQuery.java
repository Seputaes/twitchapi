package gg.sep.twitchapi.model.clips;

/**
 * Options for thr the Query String input to be used on the {@link gg.sep.twitchapi.clips.TwitchClipsAPI}.
 */
public enum ClipsQuery {
    BROADCASTER_ID("broadcaster_id"),
    GAME_ID("game_id"),
    ID("id"),
    ENDED_AT("ended_at"),
    STARTED_AT("started_at");

    private String value;

    ClipsQuery(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
