package gg.sep.twitchapi.model.clips;

import java.net.URL;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataList;
import gg.sep.twitchapi.model.DataListPaginated;
import gg.sep.twitchapi.model.DataAPIObject;
import gg.sep.twitchapi.model.serializers.LocalDateTimeAdapter;
import gg.sep.twitchapi.model.serializers.URLAdapter;

/**
 * Model for a Twitch Clips API response object.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-clips
 */
@Getter
public class TwitchClip implements APIObject {
    @SerializedName("broadcaster_id")
    private String broadcasterId;
    
    @SerializedName("broadcaster_name")
    private String broadcasterName;
    
    @SerializedName("created_at")
    private LocalDateTime createdAt;
    
    @SerializedName("creator_id")
    private String creatorId;
    
    @SerializedName("creator_name")
    private String creatorName;
    
    @SerializedName("embed_url")
    private URL embedURL;
    
    @SerializedName("game_id")
    private String gameId;
    
    private String id;
    private String language;

    @SerializedName("thumbnail_url")
    private URL thumbnailURL;

    private String name;
    private String title;
    private URL url;

    @SerializedName("video_id")
    private String videoId;

    @SerializedName("view_count")
    private long viewCount;

    /**
     * Handles the parsing of the raw {@link DataAPIObject}.
     * Handles correctly generating the GSON generic {@link TypeToken} and passing it to
     * {@link DataList#fromJson(Gson, String, TypeToken)} constructor.
     * @param json Raw JSON response for this API.
     * @return Constructed {@link DataList} consisting of Twitch Users.
     */
    public static DataList<TwitchClip> parseDataList(final String json) {
        final TypeToken<DataListPaginated<TwitchClip>> typeToken = new TypeToken<>(){};
        return DataListPaginated.fromJson(getGson(), json, typeToken);
    }

    /**
     * Builds the necessary GSON object for this API.
     * @return GSON object.
     */
    private static Gson getGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(URL.class, new URLAdapter())
            .create();
    }
    
}
