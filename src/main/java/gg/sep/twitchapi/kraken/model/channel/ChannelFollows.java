package gg.sep.twitchapi.kraken.model.channel;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractPaginated;

/**
 * Implements the Channel Follows Kraken API object.
 * https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-followers
 */
@Getter
public class ChannelFollows extends AbstractPaginated<Follower> {

    @SerializedName("follows")
    private List<Follower> items;
}
