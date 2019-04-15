package gg.sep.twitchapi.helix.model.game;

import gg.sep.twitchapi.helix.model.AbstractPaginated;

/**
 * Implements the Top Games API responses, which are paginated responses containing {@link Game} objects.
 * https://dev.twitch.tv/docs/api/reference/#get-top-games
 */
public class TopGames extends AbstractPaginated<Game> {
}
