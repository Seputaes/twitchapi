package gg.sep.twitchapi.kraken.model.video;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractKrakenObject;
import gg.sep.twitchapi.kraken.model.channel.Channel;

/**
 * Implements the Video Kraken API object.
 * https://dev.twitch.tv/docs/v5/reference/videos/
 */
@Getter
public class Video extends AbstractKrakenObject {

    @SerializedName("_id")
    private String id;
    @SerializedName("broadcast_id")
    private Long broadcastId;
    private Channel channel;
    @SerializedName("created_at")
    private ZonedDateTime createdAt;
    private String description;
    @SerializedName("description_html")
    private String descriptionHtml;
    private HashMap<String, Double> fps;
    private String game;
    private String language;
    private Long length;
    private VideoPreview preview;
    @SerializedName("published_at")
    private ZonedDateTime publishedAt;
    private HashMap<String, String> resolutions;
    private String status;
    @SerializedName("tag_list")
    private String tagList;
    private VideoThumbnails thumbnails;
    private String title;
    private URL url;
    private String viewable;
    private ZonedDateTime viewableAt;
    private Long views;







}
