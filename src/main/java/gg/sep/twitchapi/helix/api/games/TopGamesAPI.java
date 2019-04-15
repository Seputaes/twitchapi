package gg.sep.twitchapi.helix.api.games;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.api.AbstractPaginatedAPI;
import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.endpoint.GamesEndpoint;
import gg.sep.twitchapi.helix.model.game.Game;
import gg.sep.twitchapi.helix.model.game.TopGames;

/**
 * Implementation of the Twitch Helix Top Games API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-top-games
 */
@Log4j2
public class TopGamesAPI extends AbstractPaginatedAPI<Game, TopGames> {

    @Getter
    private GamesEndpoint gamesEndpoint;

    /**
     * Construct the Helix Top Games API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Top games API.
     */
    TopGamesAPI(final Helix helix) {
        super(helix);
        this.gamesEndpoint = helix.getGamesAPI().getGamesEndpoint();
    }

    /**
     * Gets a list of the current top 1000 games being streamed on Twitch (by viewer count).
     * @return List of the current top 1000 games being streamed on Twitch (by viewer count).
     */
    public List<Game> getTopGames() {
        return getTopGames(1000L);
    }

    /**
     * Gets a list of the current top games being streamed on Twitch, up to the maximum specified in {@code limit}.
     * @param limit Maximum number of results to return.
     * @return List of the current top games being streamed on Twitch, up to the maximum specified in {@code limit}.
     */
    public List<Game> getTopGames(final long limit) {
        return performPagination(getCallParams(), (double) limit);
    }

    /**
     * {@inheritDoc}
     */
    protected Optional<TopGames> innerCall(final Map<String, Object> callParams, final int first, final String cursor) {
        final Call<TopGames> call = gamesEndpoint.getTopGames(first, cursor);
        return executePaginatedCall(call);
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, String)}.
     */
    private Map<String, Object> getCallParams() {
        return new HashMap<>();
    }
}
