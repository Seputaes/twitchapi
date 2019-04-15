package gg.sep.twitchapi.helix.model.video;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Possible values to be returned in the "viewable" attribute of the {@link Video}.
 */
public enum Viewable {
    @SerializedName("public") PUBLIC("public"),
    @SerializedName("private") PRIVATE("private");

    @Getter
    private String value;

    Viewable(final String value) {
        this.value = value;
    }
}
