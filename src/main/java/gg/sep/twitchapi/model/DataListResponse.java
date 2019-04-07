package gg.sep.twitchapi.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Implementation of a Twitch API response which contains a "Data" attribute which holds the list of its objects.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
@Log4j2
public class DataListResponse<T extends APIObject> implements DataAPIObject<T> {
    @Getter private List<T> data = new ArrayList<>();

    /**
     * Builds the appropriate DataListResponse using the specified GSON (from a builder), JSON string,
     * and GSON TypeToken of this generic's type.
     * @param gson Pre-built GSON, containing any necessary custom serializers/deserializers.
     * @param json Raw JSON string of the Data List Response.
     * @param type GSON Type Token of this generic, with {@link T} specified.
     * @param <T> Type of the object contained within the Data collection parameter.
     * @return Constructed DataListResponse.
     */
    public static <T extends APIObject> DataListResponse<T> fromJson(final Gson gson, final String json,
                                                                     final TypeToken type) {
        try {
            return gson.fromJson(json, type.getType());
        } catch (final JsonSyntaxException e) {
            log.error("Error parsing JSON into model. type={}, error={}", type.getType(), e.getMessage());
            return new DataListResponse<>();
        }
    }
}
