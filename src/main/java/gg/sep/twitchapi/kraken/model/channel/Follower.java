package gg.sep.twitchapi.kraken.model.channel;

import java.time.ZonedDateTime;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractKrakenObject;
import gg.sep.twitchapi.kraken.model.user.User;

/**
 * Implements the Follower object contained in the {@link ChannelFollows} object.
 * https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-followers
 */
@Getter
public class Follower extends AbstractKrakenObject {

    @SerializedName("created_at")
    private ZonedDateTime followingSince;

    private Boolean notifications;
    private User user;
}
