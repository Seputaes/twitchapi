package gg.sep.twitchapi.helix.model.stream;

import gg.sep.twitchapi.helix.model.AbstractPaginated;

/**
 * Implements the Games API responses, which are paginated responses containing {@link Stream} objects.
 * https://dev.twitch.tv/docs/api/reference/#get-streams
 */
public class Streams extends AbstractPaginated<Stream> {
}
