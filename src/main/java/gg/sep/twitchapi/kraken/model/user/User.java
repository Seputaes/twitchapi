package gg.sep.twitchapi.kraken.model.user;

import java.net.URL;
import java.time.ZonedDateTime;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractKrakenObject;

/**
 * Implements the User Kraken API object.
 * https://dev.twitch.tv/docs/v5/reference/users/
 */
@Getter
public class User extends AbstractKrakenObject {

    @SerializedName("_id")
    private String id;

    private String bio;

    @SerializedName("created_at")
    private ZonedDateTime createdAt;

    @SerializedName("display_name")
    private String displayName;

    private URL logo;
    private String name;
    private String type;

    @SerializedName("updated_at")
    private ZonedDateTime updatedAt;
}
