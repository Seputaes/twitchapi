package gg.sep.twitchapi.model.users;

import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataList;
import gg.sep.twitchapi.model.serializers.URLAdapter;

/**
 * Model for a Twitch User API response object.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users
 */
@Getter
public class TwitchUser implements APIObject {
    private String id;
    private String login;
    private UserType type;
    private String description;
    private String email;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("broadcaster_type")
    private BroadcasterType broadcasterType;

    @SerializedName("profile_image_url")
    private URL profileImageURL;

    @SerializedName("offline_image_url")
    private URL offlineImageURL;

    @SerializedName("view_count")
    private long viewCount;

    /**
     * Handles the parsing of the raw {@link gg.sep.twitchapi.model.DataAPIObject}.
     * Handles correctly generating the GSON generic {@link TypeToken} and passing it to
     * {@link DataList#fromJson(Gson, String, TypeToken)} constructor.
     * @param json Raw JSON response for this API.
     * @return Constructed {@link DataList} consisting of Twitch Users.
     */
    public static DataList<TwitchUser> parseDataList(final String json) {
        final TypeToken<DataList<TwitchUser>> typeToken = new TypeToken<>(){};
        return DataList.fromJson(getGson(), json, typeToken);
    }

    /**
     * Builds the necessary GSON object for this API.
     * @return GSON object.
     */
    private static Gson getGson() {
        return new GsonBuilder()
            .registerTypeAdapter(URL.class, new URLAdapter())
            .create();
    }
}
