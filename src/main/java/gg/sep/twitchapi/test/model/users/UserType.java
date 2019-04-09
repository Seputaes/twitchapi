package gg.sep.twitchapi.test.model.users;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Possible values for a Twitch User's type field.
 */
public enum UserType {
    @SerializedName("staff") STAFF("staff"),
    @SerializedName("admin") ADMIN("admin"),
    @SerializedName("global_mod") GLOBAL_MOD("global_mod"),
    @SerializedName("") NONE("");

    @Getter
    private final String value;

    UserType(final String value) {
        this.value = value;
    }
}
