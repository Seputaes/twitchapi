package gg.sep.twitchapi.model.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gg.sep.twitchapi.model.DataList;
import gg.sep.twitchapi.utils.FileUtils;

/**
 * Tests for the {@link TwitchUser} class.
 */
public class TestTwitchUser {

    /**
     * Tests that a Twitch User Datalist is correctly generated from valid JSON.
     */
    @Test
    public void testTwitchUserParseValidData() {
        final DataList<TwitchUser> twitchUserDl = TwitchUser.parseDataList(getValidTwitchUserJson());
        Assertions.assertNotNull(twitchUserDl);
        Assertions.assertEquals(twitchUserDl.getData().size(), 1);

        // get the Twitch User
        final TwitchUser user = twitchUserDl.getData().get(0);

        Assertions.assertEquals(user.getId(), "1234");
        Assertions.assertEquals(user.getLogin(), "seputaes");
        Assertions.assertEquals(user.getType(), UserType.NONE);
        Assertions.assertEquals(user.getDescription(), "Some description");
        Assertions.assertEquals(user.getEmail(), "example@example.com");
        Assertions.assertEquals(user.getDisplayName(), "seputaes");
        Assertions.assertEquals(user.getBroadcasterType(), BroadcasterType.AFFILIATE);
        Assertions.assertEquals(user.getProfileImageURL().toString(), "https://example.com/profile.png");
        Assertions.assertEquals(user.getOfflineImageURL().toString(), "https://example.com/offline.png");
        Assertions.assertEquals(user.getViewCount(), 123);
    }

    /**
     * Tests that an Empty Twitch User Datalist is correctly generated, even when there's invalid JSON (no exceptions).
     */
    @Test
    public void testTwitchUserParseInvalidData() {
        final DataList<TwitchUser> twitchUserDl = TwitchUser.parseDataList(getInvalidTwitchUserJson());
        Assertions.assertNotNull(twitchUserDl);
        Assertions.assertTrue(twitchUserDl.getData().isEmpty());
    }

    private static String getValidTwitchUserJson() {
        return FileUtils.getResourceFileContents("api_json/users/valid_twitch_user.json");
    }

    private static String getInvalidTwitchUserJson() {
        return FileUtils.getResourceFileContents("api_json/users/invalid_twitch_user.json");
    }
}
