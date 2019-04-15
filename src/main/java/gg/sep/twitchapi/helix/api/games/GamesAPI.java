package gg.sep.twitchapi.helix.api.games;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.api.AbstractDataListAPI;
import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.endpoint.GamesEndpoint;
import gg.sep.twitchapi.helix.model.game.Game;
import gg.sep.twitchapi.helix.model.game.Games;

/**
 * Implementation of the Twitch Helix Games API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-games
 *
 * TODO: Implement batching for when the number of query parameters > 100
 */
@Log4j2
public class GamesAPI extends AbstractDataListAPI<Game, Games> {

    @Getter
    private GamesEndpoint gamesEndpoint;

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final TopGamesAPI topGamesAPI = initTopGamesAPI();

    /**
     * Construct the Helix Games API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Games API.
     */
    public GamesAPI(final Helix helix) {
        super(helix);
        this.gamesEndpoint = helix.getRetrofit().create(GamesEndpoint.class);
    }

    /**
     * Gets a specific Game by its ID.
     * @param id ID of the game.
     * @return Optional of the game if it is found, empty otherwise.
     */
    public Optional<Game> getGameById(final String id) {
        final List<Game> games = getGames(Collections.singletonList(id), null);
        return (games.isEmpty()) ? Optional.empty() : Optional.of(games.get(0));
    }

    /**
     * Gets a specific Game by its name.
     * @param name Name of the game.
     * @return Optional of the game if it is found, empty otherwise.
     */
    public Optional<Game> getGameByName(final String name) {
        final List<Game> games = getGames(null, Collections.singletonList(name));
        return (games.isEmpty()) ? Optional.empty() : Optional.of(games.get(0));
    }

    /**
     * Gets a list of Games by the specified list of Game IDs.
     * @param gameIds List of Game IDs.
     * @return List of Games by the specified list of Game IDs.
     */
    public List<Game> getGamesById(final List<String> gameIds) {
        return getGames(gameIds, null);
    }

    /**
     * Gets a list of Games by the specified list of Game Names.
     * @param names List of Game Names.
     * @return List of Games by the specified list of Game Names.
     */
    public List<Game> getGamesByName(final List<String> names) {
        return getGames(null, names);
    }

    /**
     * Gets a list of the current top 1000 games being streamed on Twitch (by viewer count).
     * @return List of the current top 1000 games being streamed on Twitch (by viewer count).
     */
    public List<Game> getTopGames() {
        return getTopGamesAPI().getTopGames();
    }

    /**
     * Gets a list of the current top games being streamed on Twitch, up to the maximum specified in {@code limit}.
     * @param limit Maximum number of results to return.
     * @return List of the current top games being streamed on Twitch, up to the maximum specified in {@code limit}.
     */
    public List<Game> getTopGames(final int limit) {
        return getTopGamesAPI().getTopGames((long) limit);
    }

    /**
     * Provides access to the Games API and its supported parameters.
     * @param gameIds List of Game IDs to retrieve.
     * @param names List of Game Names to retrieve.
     * @return List of Games matching the criteria.
     */
    public List<Game> getGames(final List<String> gameIds, final List<String> names) {
        final Call<Games> call = gamesEndpoint.getGames(gameIds, names);
        final Optional<Games> games = executeDataListCall(call);
        return games.map(Games::getData).orElse(Collections.emptyList());
    }

    /**
     * Initialize a new child TopGamesAPI from the current Games API.
     * @return new TopGames API based on this Streams API.
     */
    private TopGamesAPI initTopGamesAPI() {
        return new TopGamesAPI(this.getHelix());
    }
}
