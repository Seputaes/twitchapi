package gg.sep.twitchapi.kraken.model.video;

import java.util.List;

import lombok.Getter;

/**
 * Implements the VideoThumbnails subobject of {@link Video}.
 */
@Getter
public class VideoThumbnails {
    private List<VideoThumbnail> small;
    private List<VideoThumbnail> medium;
    private List<VideoThumbnail> large;
    private List<VideoThumbnail> template;
}
