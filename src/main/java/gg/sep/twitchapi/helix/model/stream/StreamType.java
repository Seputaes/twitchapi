package gg.sep.twitchapi.helix.model.stream;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Possible values to be returned in the "type" attribute of {@link Stream}.
 */
public enum StreamType {
    @SerializedName("live") LIVE("live"),
    @SerializedName("") ERROR("");

    @Getter
    private String value;

    StreamType(final String value) {
        this.value = value;
    }
}
