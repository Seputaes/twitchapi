package gg.sep.twitchapi.serializer;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Serializer/Adapter for Gson which parses ISO-8601 formatted strings into ZonedDateTime objects.
 */
public class ZonedDateTimeAdapter implements JsonDeserializer<ZonedDateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTime deserialize(final JsonElement element, final Type type,
                                     final JsonDeserializationContext context) throws JsonParseException {
        return ZonedDateTime.parse(element.getAsJsonPrimitive().getAsString());
    }
}
