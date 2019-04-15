package gg.sep.twitchapi.kraken.model.video;

import java.net.URL;

import lombok.Getter;

/**
 * Implements the VideoPreview subobject of {@link Video}.
 */
@Getter
public class VideoPreview {
    private URL large;
    private URL medium;
    private URL small;
    private URL template; // TODO: Replace with a template URL
}
