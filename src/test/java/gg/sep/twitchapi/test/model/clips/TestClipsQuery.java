package gg.sep.twitchapi.test.model.clips;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TestClipsQuery} enum.
 */
public class TestClipsQuery {

    /**
     * Tests the mappings between the enums and its values.
     * Will fail if new enum values are added and not added to the test.
     */
    @Test
    public void testClipsQueryValueMappings() {
        final Map<ClipsQuery, String> testTypes = new ConcurrentHashMap<>();
        testTypes.put(ClipsQuery.BROADCASTER_ID, "broadcaster_id");
        testTypes.put(ClipsQuery.GAME_ID, "game_id");
        testTypes.put(ClipsQuery.ENDED_AT, "ended_at");
        testTypes.put(ClipsQuery.ID, "id");
        testTypes.put(ClipsQuery.STARTED_AT, "started_at");

        for (final Map.Entry<ClipsQuery, String> entry : testTypes.entrySet()) {
            Assertions.assertEquals(entry.getKey().toString(), entry.getValue());
            testTypes.remove(entry.getKey());
        }
        Assertions.assertTrue(testTypes.isEmpty());
    }
}
