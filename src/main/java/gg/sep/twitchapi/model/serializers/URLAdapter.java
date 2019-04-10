package gg.sep.twitchapi.model.serializers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.extern.log4j.Log4j2;

/**
 * Serialization/Deserialization adapter for parsing URL strings in JSON responses.
 */
@Log4j2
public class URLAdapter implements JsonDeserializer<URL> {

    /**
     * {@inheritDoc}
     */
    @Override
    public URL deserialize(final JsonElement element, final Type type,
                           final JsonDeserializationContext context) throws JsonParseException {
        try {
            return new URL(element.getAsJsonPrimitive().getAsString());
        } catch (final IOException e) {
            if (element.getAsJsonPrimitive().getAsString().length() > 0) {
                log.error("Error parsing a non-empty URL from the Twitch API: {}", element.getAsJsonPrimitive().getAsString());
            }
            return null;
        }
    }
}
