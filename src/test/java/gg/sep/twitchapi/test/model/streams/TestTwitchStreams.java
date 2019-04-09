package gg.sep.twitchapi.test.model.streams;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gg.sep.twitchapi.test.model.DataList;
import gg.sep.twitchapi.test.model.DataListPaginated;
import gg.sep.twitchapi.test.model.clips.TwitchClip;
import gg.sep.twitchapi.test.model.users.TwitchUser;
import gg.sep.twitchapi.test.utils.FileUtils;

/**
 * Tests for the {@link TwitchStream} class.
 */
public class TestTwitchStreams {

    /**
     * Tests that a Twitch Clip Datalist is correctly generated from valid JSON.
     */
    @Test
    public void testTwitchStreamsParseValidData() {
        final DataList<TwitchStream> twitchStreamsDl = TwitchStream.parseDataList(getValidTwitchStreamsJson());
        Assertions.assertNotNull(twitchStreamsDl);
        Assertions.assertEquals(twitchStreamsDl.getData().size(), 2);

        // get the Twitch Streams
        final TwitchStream stream1 = twitchStreamsDl.getData().get(0);
        final TwitchStream stream2 = twitchStreamsDl.getData().get(1);

        Assertions.assertEquals(stream1.getId(), "1234567890");
        Assertions.assertEquals(stream1.getUserId(), "123");
        Assertions.assertEquals(stream1.getUserName(), "seputaes");
        Assertions.assertEquals(stream1.getGameId(), "456");
        Assertions.assertEquals(stream1.getCommunityIds(), Arrays.asList("abc-123-456", "cba-321-654"));
        Assertions.assertEquals(stream1.getType(), StreamType.LIVE);
        Assertions.assertEquals(stream1.getStartedAt(), LocalDateTime.of(2019, 1, 1, 1, 1, 1));
        Assertions.assertEquals(stream1.getViewerCount(), 123);
        Assertions.assertEquals(stream1.getLanguage(), "en");
        Assertions.assertEquals(stream1.getThumbnailURL().getURL().toString(), "https://example.com/tn1-640x360.jpg");
        Assertions.assertEquals(stream1.getTagIds(), Arrays.asList("def-123-456", "fed-321-654"));

        Assertions.assertEquals(stream2.getId(), "9876543210");
        Assertions.assertEquals(stream2.getUserId(), "321");
        Assertions.assertEquals(stream2.getUserName(), "kotnat");
        Assertions.assertEquals(stream2.getGameId(), "654");
        Assertions.assertEquals(stream2.getCommunityIds(), Arrays.asList("ghi-123-456", "ihg-321-654"));
        Assertions.assertEquals(stream2.getType(), StreamType.ERROR);
        Assertions.assertEquals(stream2.getStartedAt(), LocalDateTime.of(2019, 2, 2, 2, 2, 2));
        Assertions.assertEquals(stream2.getViewerCount(), 321);
        Assertions.assertEquals(stream2.getLanguage(), "en");
        Assertions.assertEquals(stream2.getThumbnailURL().getURL().toString(), "https://example.com/tn2-640x360.jpg");
        Assertions.assertEquals(stream2.getTagIds(), Arrays.asList("jkl-123-456", "lkj-321-654"));
    }

    /**
     * Tests the {@link gg.sep.twitchapi.test.model.Pagination} object within the Twitch Streams response.
     */
    @Test
    public void testTwitchStreamsPagination() {
        final DataListPaginated<TwitchStream> twitchClipDl =
            (DataListPaginated<TwitchStream>) TwitchStream.parseDataList(getValidTwitchStreamsJson());

        Assertions.assertNotNull(twitchClipDl.getPagination());
        Assertions.assertEquals(twitchClipDl.getPagination().getCursor(), "321abc");

    }

    /**
     * Tests that two streams with equal IDs evaluate to equal.
     */
    @Test
    public void testEqualTwitchStreams() {
        final DataList<TwitchStream> twitchStreamsDl = TwitchStream.parseDataList(getEqualTwitchStreamsJson());

        // get the Twitch Streams
        final TwitchStream stream1 = twitchStreamsDl.getData().get(0);
        final TwitchStream stream2 = twitchStreamsDl.getData().get(1);

        Assertions.assertEquals(stream1, stream2);

        Assertions.assertEquals(ImmutableSet.of(stream1, stream2).size(), 1);
    }

    /**
     * Tests that two streams with different IDs evaluate to unequal.
     */
    @Test
    public void testUnequalTwitchStreams() {
        final DataList<TwitchStream> twitchStreamsDl = TwitchStream.parseDataList(getValidTwitchStreamsJson());

        // get the Twitch Streams
        final TwitchStream stream1 = twitchStreamsDl.getData().get(0);
        final TwitchStream stream2 = twitchStreamsDl.getData().get(1);

        Assertions.assertNotEquals(stream1, stream2);
        Assertions.assertNotEquals(stream1, new TwitchClip());
        Assertions.assertEquals(ImmutableSet.of(stream1, stream2).size(), 2);
    }

    /**
     * Tests that streams are correctly sorted by viewer count.
     */
    @Test
    public void testTwitchStreamsCompare() {
        final DataList<TwitchStream> twitchStreamsDl = TwitchStream.parseDataList(getValidTwitchStreamsJson());

        // get the Twitch Streams
        final TwitchStream stream1 = twitchStreamsDl.getData().get(0);
        final TwitchStream stream2 = twitchStreamsDl.getData().get(1);

        // make sure it sorts correctly
        final List<TwitchStream> sort1 = Arrays.asList(stream1, stream2);
        Collections.sort(sort1);
        Assertions.assertEquals(sort1.get(0), stream1);
        Assertions.assertEquals(sort1.get(1), stream2);

        final List<TwitchStream> sort2 = Arrays.asList(stream1, stream2);
        Collections.sort(sort2);
        Assertions.assertEquals(sort2.get(0), stream1);
        Assertions.assertEquals(sort2.get(1), stream2);
    }

    /**
     * Tests that attempting to compare to null throws an exception.
     */
    @Test
    public void testTwitchStreamsNullCompare() {
        Assertions.assertThrows(NullPointerException.class, () -> new TwitchStream().compareTo(null));
    }

    /**
     * Tests that an Empty Twitch User Datalist is correctly generated, even when there's invalid JSON (no exceptions).
     */
    @Test
    public void testTwitchStreamsParseInvalidData() {
        final DataList<TwitchUser> twitchUserDl = TwitchUser.parseDataList(getInvalidTwitchStreamsJson());
        Assertions.assertNotNull(twitchUserDl);
        Assertions.assertTrue(twitchUserDl.getData().isEmpty());
    }

    private static String getValidTwitchStreamsJson() {
        return FileUtils.getResourceFileContents("api_json/streams/valid_twitch_streams.json");
    }

    private static String getEqualTwitchStreamsJson() {
        return FileUtils.getResourceFileContents("api_json/streams/equal_twitch_streams.json");
    }

    private static String getInvalidTwitchStreamsJson() {
        return FileUtils.getResourceFileContents("api_json/streams/invalid_twitch_streams.json");
    }
}
