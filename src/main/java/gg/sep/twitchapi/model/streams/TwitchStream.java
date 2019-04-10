package gg.sep.twitchapi.model.streams;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NonNull;

import gg.sep.twitchapi.model.APIObject;
import gg.sep.twitchapi.model.DataList;
import gg.sep.twitchapi.model.DataListPaginated;
import gg.sep.twitchapi.model.DataAPIObject;
import gg.sep.twitchapi.model.serializers.LocalDateTimeAdapter;
import gg.sep.twitchapi.model.serializers.ThumbnailURLAdapter;

/**
 * Model for a Twitch Stream API response object.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-streams
 */
@Getter
public class TwitchStream implements APIObject, Comparable<TwitchStream> {

    @SerializedName("community_ids")
    private List<String> communityIds;

    @SerializedName("game_id")
    private String gameId;

    private String id;
    private String language;

    @SerializedName("started_at")
    private LocalDateTime startedAt;

    @SerializedName("tag_ids")
    private List<String> tagIds;

    @SerializedName("thumbnail_url")
    private ThumbnailURL thumbnailURL;

    private String title;
    private StreamType type;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("viewer_count")
    private long viewerCount;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TwitchStream)) {
            return false;
        }
        final TwitchStream castStream = (TwitchStream) o;
        return this.getId().equals(castStream.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(@NonNull final TwitchStream other) {
        return Long.valueOf(this.getViewerCount()).intValue() - Long.valueOf(other.getViewerCount()).intValue();
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    /**
     * Handles the parsing of the raw {@link DataAPIObject}.
     * Handles correctly generating the GSON generic {@link TypeToken} and passing it to
     * {@link DataList#fromJson(Gson, String, TypeToken)} constructor.
     * @param json Raw JSON response for this API.
     * @return Constructed {@link DataList} consisting of Twitch Streams.
     */
    public static DataList<TwitchStream> parseDataList(final String json) {
        final TypeToken<DataListPaginated<TwitchStream>> typeToken = new TypeToken<>(){};
        return DataListPaginated.fromJson(getGson(), json, typeToken);
    }

    /**
     * Builds the necessary GSON object for this API.
     * @return GSON object.
     */
    private static Gson getGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ThumbnailURL.class, new ThumbnailURLAdapter())
            .create();
    }
}


