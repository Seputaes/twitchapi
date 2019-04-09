package gg.sep.twitchapi.test.model.serializers;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Serialization/Deserialization adapter for parsing Date/Time strings in JSON responses.
 */
public class LocalDateTimeAdapter implements JsonDeserializer<LocalDateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime deserialize(final JsonElement element, final Type type,
                                     final JsonDeserializationContext context) throws JsonParseException {
        return ZonedDateTime.parse(element.getAsJsonPrimitive().getAsString()).toLocalDateTime();
    }
}
