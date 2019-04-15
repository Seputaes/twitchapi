package gg.sep.twitchapi.helix.model.tag;

import gg.sep.twitchapi.helix.model.AbstractPaginated;

/**
 * Implements the Tags API responses, which are paginated responses containing {@link Tag} objects.
 * https://dev.twitch.tv/docs/api/reference/#get-stream-tags
 */
public class Tags extends AbstractPaginated<Tag> {
}
