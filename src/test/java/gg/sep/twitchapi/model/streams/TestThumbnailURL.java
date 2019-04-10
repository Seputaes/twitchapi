package gg.sep.twitchapi.model.streams;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link ThumbnailURL} model.
 */
public class TestThumbnailURL {

    private static final String VALID_URL = "https://www.example.com/path_{width}x{height}.png";
    private static final String INVALID_URL = "invalid_url";

    /**
     * Tests that invalid URLs throw an exception when constructing.
     */
    @Test
    public void testMalformedURL() {
        Assertions.assertThrows(MalformedURLException.class, () -> new ThumbnailURL(INVALID_URL));
    }

    /**
     * Tests that the class works with URLs which don't contain any width/height placeholders.
     * @throws Exception Exception thrown if our test URL is invalid.
     */
    @Test
    public void testValidURLNoReplacements() throws Exception {
        final String testUrl = "https://www.example.com/path?query=1&var=2";
        final ThumbnailURL thumbnailURL = new ThumbnailURL(testUrl);

        Assertions.assertEquals(thumbnailURL.getURL().toString(), testUrl);
        Assertions.assertEquals(thumbnailURL.getURL(30, 40).toString(), testUrl);
    }

    /**
     * Tests that the default width/height replacements get substituted in.
     * @throws Exception Exception thrown if our test URL is invalid.
     */
    @Test
    public void testValidURLDefaultReplacements() throws Exception {
        final String expectedDefault = "https://www.example.com/path_640x360.png";

        final ThumbnailURL thumbnailURL = new ThumbnailURL(VALID_URL);

        Assertions.assertEquals(thumbnailURL.getURL().toString(), expectedDefault);
    }

    /**
     * Tests that custom width/height values get substituted in.
     * @throws Exception Exception thrown if our test URL is invalid.
     */
    @Test
    public void testValidURLCustomReplacements() throws Exception {
        final String expectedSubstitution = "https://www.example.com/path_1280x720.png";
        final ThumbnailURL thumbnailURL = new ThumbnailURL(VALID_URL);

        Assertions.assertEquals(thumbnailURL.getURL(1280, 720).toString(), expectedSubstitution);
    }
}
