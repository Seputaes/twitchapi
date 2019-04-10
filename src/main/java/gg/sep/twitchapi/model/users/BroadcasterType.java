package gg.sep.twitchapi.model.users;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Possible values for a Twitch User's Broadcaster Type field.
 */
public enum BroadcasterType {
    @SerializedName("partner") PARTNER("partner"),
    @SerializedName("affiliate") AFFILIATE("affiliate"),
    @SerializedName("") NONE("");

    @Getter
    private final String value;

    BroadcasterType(final String value) {
        this.value = value;
    }
}
