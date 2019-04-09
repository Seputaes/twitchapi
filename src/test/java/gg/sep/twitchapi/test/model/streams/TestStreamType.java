package gg.sep.twitchapi.test.model.streams;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TestStreamType} enum.
 */
public class TestStreamType {

    /**
     * Tests the mappings between the enums and its values.
     * Will fail if new enum values are added and not added to the test.
     */
    @Test
    public void testStreamTypeValueMappings() {
        final Map<StreamType, String> testTypes = new ConcurrentHashMap<>();
        testTypes.put(StreamType.LIVE, "live");
        testTypes.put(StreamType.ERROR, "");

        for (final Map.Entry<StreamType, String> entry : testTypes.entrySet()) {
            Assertions.assertEquals(entry.getKey().getValue(), entry.getValue());
            testTypes.remove(entry.getKey());
        }
        Assertions.assertTrue(testTypes.isEmpty());
    }
}
