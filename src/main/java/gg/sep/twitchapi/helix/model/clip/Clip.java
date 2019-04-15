package gg.sep.twitchapi.helix.model.clip;

import java.net.URL;
import java.time.ZonedDateTime;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the Clip Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-clips
 */
@Getter
public class Clip extends AbstractHelixObject {
    private String id;
    private URL url;
    @SerializedName("embed_url")
    private URL embedURL;
    @SerializedName("broadcaster_id")
    private String broadcasterId;
    @SerializedName("broadcaster_name")
    private String broadcasterName;
    @SerializedName("creator_id")
    private String creatorId;
    @SerializedName("creator_name")
    private String creatorName;
    @SerializedName("video_id")
    private String videoId;
    @SerializedName("game_id")
    private String gameId;
    private String language;
    private String title;
    @SerializedName("view_count")
    private Long viewCount;
    @SerializedName("created_at")
    private ZonedDateTime createdAt;
    @SerializedName("thumbnail_url")
    private URL thumbnailURL;
}
