package gg.sep.twitchapi.helix.model.stream;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the Stream Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-streams
 */
@Getter
public class Stream extends AbstractHelixObject {
    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("game_id")
    private String gameId;
    @SerializedName("community_ids")
    private List<String> communityIds;
    private StreamType type;
    private String title;
    @SerializedName("viewer_count")
    private Long viewerCount;
    @SerializedName("started_at")
    private ZonedDateTime startedAt;
    private String language;
    @SerializedName("thumbnail_url")
    private URL thumbnailURL; // TODO: Template URL
    @SerializedName("tag_ids")
    private List<String> tagIds;
}
