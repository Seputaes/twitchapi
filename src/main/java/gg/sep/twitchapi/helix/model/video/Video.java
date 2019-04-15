package gg.sep.twitchapi.helix.model.video;

import java.net.URL;
import java.time.ZonedDateTime;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the Video Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-videos
 */
@Getter
public class Video extends AbstractHelixObject {

    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_name")
    private String userName;
    private String title;
    private String description;
    @SerializedName("created_at")
    private ZonedDateTime createdAt;
    @SerializedName("published_at")
    private ZonedDateTime publishedAt;
    private URL url;
    @SerializedName("thumbnail_url")
    private URL thumbnailURL; // TODO: Template
    private Viewable viewable;
    private String language;
    @SerializedName("view_count")
    private Long viewCount;
    private VideoType type;
    private String duration;

}
