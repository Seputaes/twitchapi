package gg.sep.twitchapi.helix.model.user;

import java.time.ZonedDateTime;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the Follow Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-users-follows
 */
@Getter
public class Follow extends AbstractHelixObject {
    @SerializedName("from_id")
    private String fromId;
    @SerializedName("from_name")
    private String fromName;
    @SerializedName("to_id")
    private String toId;
    @SerializedName("to_name")
    private String toName;
    @SerializedName("followed_at")
    private ZonedDateTime followedAt;
}
