package gg.sep.twitchapi.helix.model.video;

/**
 * Valid values to be used in the {@link gg.sep.twitchapi.helix.api.videos.VideosAPI} type field.
 */
public enum VideoTypeQuery {

    ALL("all"),
    UPLOAD("upload"),
    ARCHIVE("archive"),
    HIGHLIGHT("highlight");

    private String value;

    VideoTypeQuery(final String value) {
        this.value = value;
    }

    /**
     * Returns a string value of the enum, suitable for use in query parameters.
     * @return String value of the enum, suitable for use in query parameters.
     */
    @Override
    public String toString() {
        return this.value;
    }
}
