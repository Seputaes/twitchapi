package gg.sep.twitchapi.model.serializers;

import java.net.MalformedURLException;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import gg.sep.twitchapi.model.streams.ThumbnailURL;

/**
 * Tests for the {@link ThumbnailURLAdapter} Gson Adapter.
 */
@RunWith(PowerMockRunner.class)
public class TestThumbnailURLAdapter {

    /**
     * Test that an invalid url which throws an exception will instead return null.
     * @throws Exception Exception thrown if constructing the {@link ThumbnailURL} fails.
     */
    @Test
    public void testDeserializeInvalidURLReturnsNull() throws Exception {
        PowerMockito.whenNew(ThumbnailURL.class)
            .withArguments(ArgumentMatchers.anyString())
            .thenThrow(new MalformedURLException());

        final ThumbnailURLAdapter adapter = new ThumbnailURLAdapter();

        // test with empty string and invalid url
        final JsonPrimitive invalidPrimitive = new JsonPrimitive("invalid_url");
        final ThumbnailURL invalidTN = adapter.deserialize(invalidPrimitive, null, null);
        Assertions.assertNull(invalidTN);

        final JsonPrimitive emptyPrimitive = new JsonPrimitive("");
        final ThumbnailURL emptyTN = adapter.deserialize(emptyPrimitive, null, null);
        Assertions.assertNull(emptyTN);
    }
}
