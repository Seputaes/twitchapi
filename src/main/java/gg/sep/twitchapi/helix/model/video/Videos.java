package gg.sep.twitchapi.helix.model.video;

import gg.sep.twitchapi.helix.model.AbstractPaginated;

/**
 * Implements the Games API responses, which are paginated responses containing {@link Video} objects.
 * https://dev.twitch.tv/docs/api/reference/#get-videos
 */
public class Videos extends AbstractPaginated<Video> {
}
