package gg.sep.twitchapi.helix.model.video;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Possible values to be returned in the "type" attribute of the {@link Video}.
 */
public enum VideoType {
    @SerializedName("upload") UPLOAD("upload"),
    @SerializedName("archive") ARCHIVE("archive"),
    @SerializedName("highlight") HIGHLIGHT("highlight");

    @Getter
    private String value;

    VideoType(final String value) {
        this.value = value;
    }
}
