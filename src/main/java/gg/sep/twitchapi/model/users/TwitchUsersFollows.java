package gg.sep.twitchapi.model.users;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataList;
import gg.sep.twitchapi.model.DataListTotalPaginated;
import gg.sep.twitchapi.model.DataAPIObject;
import gg.sep.twitchapi.model.serializers.LocalDateTimeAdapter;

/**
 * Model for a Twitch Users Follows API response object.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users-follows
 */
@Getter
public class TwitchUsersFollows implements APIObject {
    private TwitchUser twitchUser;

    @SerializedName("from_id")
    private String fromId;

    @SerializedName("from_name")
    private String fromName;

    @SerializedName("to_id")
    private String toId;

    @SerializedName("to_name")
    private String toName;

    @SerializedName("followed_at")
    private LocalDateTime followedAt;

    /**
     * Handles the parsing of the raw {@link DataAPIObject}.
     * Handles correctly generating the GSON generic {@link TypeToken} and passing it to
     * {@link DataList#fromJson(Gson, String, TypeToken)} constructor.
     * @param json Raw JSON response for this API.
     * @return Constructed {@link DataList} consisting of Twitch Users Follows.
     */
    public static DataList<TwitchUsersFollows> parseDataList(final String json) {
        final TypeToken<DataListTotalPaginated<TwitchUsersFollows>> typeToken = new TypeToken<>(){};
        return DataListTotalPaginated.fromJson(getGson(), json, typeToken);
    }

    /**
     * Builds the necessary GSON object for this API.
     * Contains a custom serializer for UTC dates.
     * @return GSON object.
     */
    private static Gson getGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    }
}
