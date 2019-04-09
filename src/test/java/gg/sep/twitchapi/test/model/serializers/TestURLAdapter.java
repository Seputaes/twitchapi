package gg.sep.twitchapi.test.model.serializers;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests for the {@link URL} Gson Adapter.
 */
@RunWith(PowerMockRunner.class)
public class TestURLAdapter {

    /**
     * Test that an invalid url which throws an exception will instead return null.
     * @throws Exception Exception thrown if constructing the {@link URL} fails.
     */
    @Test
    public void testDeserializeInvalidURLReturnsNull() throws Exception {
        PowerMockito.whenNew(URL.class)
            .withArguments(ArgumentMatchers.anyString())
            .thenThrow(new MalformedURLException());

        final URLAdapter adapter = new URLAdapter();

        // test with empty string and invalid url
        final JsonPrimitive invalidPrimitive = new JsonPrimitive("invalid_url");
        final URL invalidURL = adapter.deserialize(invalidPrimitive, null, null);
        Assertions.assertNull(invalidURL);

        final JsonPrimitive emptyPrimitive = new JsonPrimitive("");
        final URL emptyURL = adapter.deserialize(emptyPrimitive, null, null);
        Assertions.assertNull(emptyURL);
    }
}
