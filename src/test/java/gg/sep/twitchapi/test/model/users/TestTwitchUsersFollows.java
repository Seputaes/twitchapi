package gg.sep.twitchapi.test.model.users;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gg.sep.twitchapi.test.model.DataList;
import gg.sep.twitchapi.test.model.DataListTotalPaginated;
import gg.sep.twitchapi.test.utils.FileUtils;

/**
 * Tests for the {@link TwitchUsersFollows} class.
 */
public class TestTwitchUsersFollows {

    /**
     * Tests that a Twitch User Datalist is correctly generated from valid JSON.
     */
    @Test
    public void testTwitchUserParseValidData() {
        final DataListTotalPaginated<TwitchUsersFollows> twitchUserDl =
            (DataListTotalPaginated<TwitchUsersFollows>) TwitchUsersFollows.parseDataList(
                getValidTwitchUsersFollowsJson());
        Assertions.assertNotNull(twitchUserDl);
        Assertions.assertEquals(twitchUserDl.getData().size(), 3);

        // validate the total field
        Assertions.assertEquals(twitchUserDl.getTotal(), 222);

        // get the first Twitch Users Follows
        final TwitchUsersFollows follows = twitchUserDl.getData().get(0);

        Assertions.assertEquals(follows.getFromId(), "1234");
        Assertions.assertEquals(follows.getFromName(), "from_name1");
        Assertions.assertEquals(follows.getToId(), "5678");
        Assertions.assertEquals(follows.getFollowedAt(), LocalDateTime.of(2019, 1, 1, 1, 1, 1));
    }

    /**
     * Tests the {@link gg.sep.twitchapi.test.model.Pagination} object within the Twitch Users Follows response.
     */
    @Test
    public void testTwitchUsersFollowsPagination() {
        final DataListTotalPaginated<TwitchUsersFollows> twitchUserDl =
            (DataListTotalPaginated<TwitchUsersFollows>) TwitchUsersFollows.parseDataList(
                getValidTwitchUsersFollowsJson());
        Assertions.assertNotNull(twitchUserDl.getPagination());
        Assertions.assertEquals(twitchUserDl.getPagination().getCursor(), "abc123");
    }

    /**
     * Tests that an Empty Twitch User Datalist is correctly generated, even when there's invalid JSON (no exceptions).
     */
    @Test
    public void testTwitchUserParseInvalidData() {
        final DataList<TwitchUsersFollows> twitchUserDl = TwitchUsersFollows.parseDataList(
            getInvalidTwitchUsersFollowsJson());
        Assertions.assertNotNull(twitchUserDl);
        Assertions.assertTrue(twitchUserDl.getData().isEmpty());
    }

    private static String getValidTwitchUsersFollowsJson() {
        return FileUtils.getResourceFileContents("api_json/users/valid_twitch_users_follows.json");
    }

    private static String getInvalidTwitchUsersFollowsJson() {
        return FileUtils.getResourceFileContents("api_json/users/invalid_twitch_users_follows.json");
    }
}
