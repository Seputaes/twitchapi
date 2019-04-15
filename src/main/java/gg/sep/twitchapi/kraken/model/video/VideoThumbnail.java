package gg.sep.twitchapi.kraken.model.video;

import java.net.URL;

import lombok.Getter;

/**
 * Implements the VideoThumbnail subobject of {@link VideoThumbnails}.
 */
@Getter
public class VideoThumbnail {
    private String type;
    private URL url;
}
