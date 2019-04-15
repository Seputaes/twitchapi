package gg.sep.twitchapi.helix.model.clip;

import gg.sep.twitchapi.helix.model.AbstractPaginated;

/**
 * Implements the Clips API responses, which are paginated responses containing {@link Clip} objects.
 * https://dev.twitch.tv/docs/api/reference/#get-clips
 */
public class Clips extends AbstractPaginated<Clip> {
}
