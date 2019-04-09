package gg.sep.twitchapi.test.model.streams;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a one of the several Thumbnail URL strings that can return from the Twitch API.
 * These URLs contain {width} and {height} placeholders for retrieving the image.
 * This class provides methods to get the URL with default width/height values, or specify your own.
 */
public class ThumbnailURL {

    private static final String WIDTH_REPLACE = "\\{width}";
    private static final String HEIGHT_REPLACE = "\\{height}";
    private static final Integer DEFAULT_WIDTH = 640;
    private static final Integer DEFAULT_HEIGHT = 360;

    private final URL innerUrl;
    private final String innerUrlString;

    /**
     * Construct the Thumbnail URL from the specified string.
     * Performs standard Java {@link URL} validation, and will throw an exception if invalid.
     * @param url String of the URL from the API.
     * @throws MalformedURLException Thrown if the URl is not a valid string.
     */
    public ThumbnailURL(final String url) throws MalformedURLException {
        this.innerUrl = new URL(formatUrl(url, DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.innerUrlString = url;
    }

    private static String formatUrl(final String url, final Integer width, final Integer height) {
        return url
            .replaceAll(WIDTH_REPLACE, width.toString())
            .replaceAll(HEIGHT_REPLACE, height.toString());
    }

    /**
     * Gets the raw URL using the default width/height values.
     * @return URL object for the Thumbnail URL.
     */
    public URL getURL() {
        return this.innerUrl;
    }

    /**
     * Gets the URL using the specified width and height values.
     * @param width Width of the image.
     * @param height Height of the image.
     * @return URL with the width/height placeholders replaced.
     * @throws MalformedURLException Exception thrown if the completed URL is invalid.
     */
    public URL getURL(final Integer width, final Integer height) throws MalformedURLException {
        return new URL(formatUrl(this.innerUrlString, width, height));
    }
}
