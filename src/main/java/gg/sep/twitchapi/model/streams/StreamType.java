package gg.sep.twitchapi.model.streams;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.streams.TwitchStreamsAPI;

/**
 * Possible values to be returned in the "type" attribute of the {@link TwitchStreamsAPI}.
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
