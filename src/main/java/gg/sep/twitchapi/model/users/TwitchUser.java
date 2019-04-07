package gg.sep.twitchapi.model.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataListResponse;

/**
 * Model for a Twitch User API response object.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users
 */
@Getter
public class TwitchUser implements APIObject {
    private String id;
    private String login;
    private String type;
    private String description;
    private int viewCount;
    private String email;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("broadcaster_type")
    private String broadcasterType; // TODO: Make this an enum

    @SerializedName("profile_image_url")
    private String profileImageURL; // TODO: Make this a URL

    @SerializedName("offline_image_url")
    private String offlineImageURL; // TODO: Make this a URL

    /**
     * Handles the parsing of the raw {@link gg.sep.twitchapi.model.DataAPIObject}.
     * Handles correctly generating the GSON generic {@link TypeToken} and passing it to
     * {@link DataListResponse#fromJson(Gson, String, TypeToken)} constructor.
     * @param json Raw JSON response for this API.
     * @return Constructed {@link DataListResponse} consisting of Twitch Users.
     */
    public static DataListResponse<TwitchUser> parseDataList(final String json) {
        final TypeToken<DataListResponse<TwitchUser>> typeToken = new TypeToken<>(){};
        return DataListResponse.fromJson(getGson(), json, typeToken);
    }

    /**
     * Builds the necessary GSON object for this API.
     * @return GSON object.
     */
    private static Gson getGson() {
        return new GsonBuilder().create();
    }
}
