package gg.sep.twitchapi.model.streams;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TestStreamsQuery} enum.
 */
public class TestStreamsQuery {

    /**
     * Tests the mappings between the enums and its values.
     * Will fail if new enum values are added and not added to the test.
     */
    @Test
    public void testStreamsQueryValueMappings() {
        final Map<StreamsQuery, String> testTypes = new ConcurrentHashMap<>();
        testTypes.put(StreamsQuery.COMMUNITY_ID, "community_id");
        testTypes.put(StreamsQuery.GAME_ID, "game_id");
        testTypes.put(StreamsQuery.LANGUAGE, "language");
        testTypes.put(StreamsQuery.USER_ID, "user_id");
        testTypes.put(StreamsQuery.USER_LOGIN, "user_login");

        for (final Map.Entry<StreamsQuery, String> entry : testTypes.entrySet()) {
            Assertions.assertEquals(entry.getKey().toString(), entry.getValue());
            testTypes.remove(entry.getKey());
        }
        Assertions.assertTrue(testTypes.isEmpty());
    }
}
