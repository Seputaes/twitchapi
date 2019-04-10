package gg.sep.twitchapi.model.users;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TestUsersFollowsQuery} enum.
 */
public class TestUsersFollowsQuery {

    /**
     * Tests the mappings between the enums and its values.
     * Will fail if new enum values are added and not added to the test.
     */
    @Test
    public void testUsersFollowsQueryValueMappings() {
        final Map<UsersFollowsQuery, String> testTypes = new ConcurrentHashMap<>();
        testTypes.put(UsersFollowsQuery.FROM_ID, "from_id");
        testTypes.put(UsersFollowsQuery.TO_ID, "to_id");

        for (final Map.Entry<UsersFollowsQuery, String> entry : testTypes.entrySet()) {
            Assertions.assertEquals(entry.getKey().toString(), entry.getValue());
            testTypes.remove(entry.getKey());
        }
        Assertions.assertTrue(testTypes.isEmpty());
    }
}
