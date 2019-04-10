package gg.sep.twitchapi.model.users;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TestUsersQuery} enum.
 */
public class TestUsersQuery {

    /**
     * Tests the mappings between the enums and its values.
     * Will fail if new enum values are added and not added to the test.
     */
    @Test
    public void testUsersQueryValueMappings() {
        final Map<UsersQuery, String> testTypes = new ConcurrentHashMap<>();
        testTypes.put(UsersQuery.ID, "id");
        testTypes.put(UsersQuery.LOGIN, "login");

        for (final Map.Entry<UsersQuery, String> entry : testTypes.entrySet()) {
            Assertions.assertEquals(entry.getKey().toString(), entry.getValue());
            testTypes.remove(entry.getKey());
        }
        Assertions.assertTrue(testTypes.isEmpty());
    }
}
