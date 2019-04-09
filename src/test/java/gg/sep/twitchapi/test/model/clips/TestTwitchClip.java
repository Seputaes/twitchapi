package gg.sep.twitchapi.test.model.clips;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gg.sep.twitchapi.test.model.DataList;
import gg.sep.twitchapi.test.model.DataListPaginated;
import gg.sep.twitchapi.test.model.users.TwitchUser;
import gg.sep.twitchapi.test.utils.FileUtils;

/**
 * Tests for the {@link TwitchClip} class.
 */
public class TestTwitchClip {

    /**
     * Tests that a Twitch Clip Datalist is correctly generated from valid JSON.
     */
    @Test
    public void testTwitchClipParseValidData() {
        final DataList<TwitchClip> twitchClipDl = TwitchClip.parseDataList(getValidTwitchClipJson());
        Assertions.assertNotNull(twitchClipDl);
        Assertions.assertEquals(twitchClipDl.getData().size(), 2);

        // get the Twitch User
        final TwitchClip clip1 = twitchClipDl.getData().get(0);
        final TwitchClip clip2 = twitchClipDl.getData().get(1);


        Assertions.assertEquals(clip1.getId(), "clipOneId");
        Assertions.assertEquals(clip1.getUrl().toString(), "https://example.com/url1");
        Assertions.assertEquals(clip1.getEmbedURL().toString(), "https://example.com/embed1");
        Assertions.assertEquals(clip1.getBroadcasterId(), "123");
        Assertions.assertEquals(clip1.getBroadcasterName(), "seputaes");
        Assertions.assertEquals(clip1.getCreatorId(), "456");
        Assertions.assertEquals(clip1.getCreatorName(), "kotnat");
        Assertions.assertEquals(clip1.getVideoId(), "");
        Assertions.assertEquals(clip1.getGameId(), "789");
        Assertions.assertEquals(clip1.getLanguage(), "en");
        Assertions.assertEquals(clip1.getTitle(), "title1");
        Assertions.assertEquals(clip1.getViewCount(), 12);
        Assertions.assertEquals(clip1.getCreatedAt(), LocalDateTime.of(2019, 1, 1, 1, 1, 1));
        Assertions.assertEquals(clip1.getThumbnailURL().toString(), "https://example.com/thumbnail1");

        Assertions.assertEquals(clip2.getId(), "clipTwoId");
        Assertions.assertEquals(clip2.getUrl().toString(), "https://example.com/url2");
        Assertions.assertEquals(clip2.getEmbedURL().toString(), "https://example.com/embed2");
        Assertions.assertEquals(clip2.getBroadcasterId(), "321");
        Assertions.assertEquals(clip2.getBroadcasterName(), "seputaes");
        Assertions.assertEquals(clip2.getCreatorId(), "654");
        Assertions.assertEquals(clip2.getCreatorName(), "kotnat");
        Assertions.assertEquals(clip2.getVideoId(), "");
        Assertions.assertEquals(clip2.getGameId(), "987");
        Assertions.assertEquals(clip2.getLanguage(), "en");
        Assertions.assertEquals(clip2.getTitle(), "title2");
        Assertions.assertEquals(clip2.getViewCount(), 21);
        Assertions.assertEquals(clip2.getCreatedAt(), LocalDateTime.of(2019, 2, 2, 2, 2, 2));
        Assertions.assertEquals(clip2.getThumbnailURL().toString(), "https://example.com/thumbnail2");
    }

    /**
     * Tests the {@link gg.sep.twitchapi.test.model.Pagination} object within the Twitch Clip response.
     */
    @Test
    public void testTwitchClipPagination() {
        final DataListPaginated<TwitchClip> twitchClipDl =
            (DataListPaginated<TwitchClip>) TwitchClip.parseDataList(getValidTwitchClipJson());

        Assertions.assertNotNull(twitchClipDl.getPagination());
        Assertions.assertEquals(twitchClipDl.getPagination().getCursor(), "abc123");

    }

    /**
     * Tests that an Empty Twitch User Datalist is correctly generated, even when there's invalid JSON (no exceptions).
     */
    @Test
    public void testTwitchClipParseInvalidData() {
        final DataList<TwitchUser> twitchUserDl = TwitchUser.parseDataList(getInvalidTwitchClipJson());
        Assertions.assertNotNull(twitchUserDl);
        Assertions.assertTrue(twitchUserDl.getData().isEmpty());
    }

    private static String getValidTwitchClipJson() {
        return FileUtils.getResourceFileContents("api_json/clips/valid_twitch_clips.json");
    }

    private static String getInvalidTwitchClipJson() {
        return FileUtils.getResourceFileContents("api_json/clips/invalid_twitch_clips.json");
    }
}
