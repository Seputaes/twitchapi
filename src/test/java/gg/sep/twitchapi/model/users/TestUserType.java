package gg.sep.twitchapi.model.users;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TestUserType} enum.
 */
public class TestUserType {

    /**
     * Tests the mappings between the enums and its values.
     * Will fail if new enum values are added and not added to the test.
     */
    @Test
    public void testUserTypeValueMappings() {
        final Map<UserType, String> testTypes = new ConcurrentHashMap<>();
        testTypes.put(UserType.ADMIN, "admin");
        testTypes.put(UserType.STAFF, "staff");
        testTypes.put(UserType.GLOBAL_MOD, "global_mod");
        testTypes.put(UserType.NONE, "");

        for (final Map.Entry<UserType, String> entry : testTypes.entrySet()) {
            Assertions.assertEquals(entry.getKey().getValue(), entry.getValue());
            testTypes.remove(entry.getKey());
        }
        Assertions.assertTrue(testTypes.isEmpty());
    }
}
