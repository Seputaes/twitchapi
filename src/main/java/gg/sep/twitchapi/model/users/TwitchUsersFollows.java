package gg.sep.twitchapi.model.users;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataListResponse;
import gg.sep.twitchapi.model.DataListResponsePaginated;

/**
 * Model for a Twitch Users Follows API response object.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-users-follows
 */
@Getter
public class TwitchUsersFollows implements APIObject {
    public TwitchUser twitchUser;

    @SerializedName("from_id")
    public String fromId;

    @SerializedName("from_name")
    public String fromName;

    @SerializedName("to_id")
    public String toId;

    @SerializedName("to_name")
    public String toName;

    @SerializedName("followed_at")
    public LocalDateTime followedAt;

    /**
     * Handles the parsing of the raw {@link gg.sep.twitchapi.model.DataAPIObject}.
     * Handles correctly generating the GSON generic {@link TypeToken} and passing it to
     * {@link DataListResponse#fromJson(Gson, String, TypeToken)} constructor.
     * @param json Raw JSON response for this API.
     * @return Constructed {@link DataListResponse} consisting of Twitch Users Follows.
     */
    public static DataListResponse<TwitchUsersFollows> parseDataList(final String json) {
        final TypeToken<DataListResponsePaginated<TwitchUsersFollows>> typeToken = new TypeToken<>(){};
        return DataListResponsePaginated.fromJson(getGson(), json, typeToken);
    }

    /**
     * Builds the necessary GSON object for this API.
     * Contains a custom serializer for UTC dates.
     * @return GSON object.
     */
    private static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(final JsonElement json, final Type type,
                                             final JsonDeserializationContext context) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
        }).create();
    }
}