package gg.sep.twitchapi.kraken.model.channel;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractPaginated;
import gg.sep.twitchapi.kraken.model.video.Video;

/**
 * Implements the Channel Videos Kraken API object.
 * https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-videos
 */
@Getter
public class ChannelVideos extends AbstractPaginated<Video> {
    @SerializedName("videos")
    private List<Video> items;
}
