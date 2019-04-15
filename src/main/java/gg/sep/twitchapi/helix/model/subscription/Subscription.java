package gg.sep.twitchapi.helix.model.subscription;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the Subscriptions Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-broadcaster-subscriptions
 */
@Getter
public class Subscription extends AbstractHelixObject {

    @SerializedName("broadcaster_id")
    private String broadcasterId;

    @SerializedName("broadcaster_name")
    private String broadcasterName;

    @SerializedName("is_gift")
    private Boolean isGift;

    private String tier;

    @SerializedName("plan_name")
    private String planName;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;
}
