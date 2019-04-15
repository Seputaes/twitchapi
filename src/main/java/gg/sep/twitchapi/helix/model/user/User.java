package gg.sep.twitchapi.helix.model.user;

import java.net.URL;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the User Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-users
 */
@Getter
public class User extends AbstractHelixObject {
    private String id;
    private String login;
    @SerializedName("display_name")
    private String displayName;
    private UserType type;
    @SerializedName("broadcaster_type")
    private BroadcasterType broadcasterType;
    private String description;
    @SerializedName("profile_image_url")
    private URL profileImageURL;
    @SerializedName("offline_image_url")
    private URL offlineImageURL;
    @SerializedName("view_count")
    private Long viewCount;
}
