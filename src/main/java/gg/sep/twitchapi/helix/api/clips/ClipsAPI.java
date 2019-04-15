package gg.sep.twitchapi.helix.api.clips;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.api.AbstractPaginatedAPI;
import gg.sep.twitchapi.helix.endpoint.ClipsEndpoint;
import gg.sep.twitchapi.helix.model.clip.Clip;
import gg.sep.twitchapi.helix.model.clip.Clips;

/**
 * Implementation of the Twitch Helix Clips API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-clips
 *
 * TODO: Implement batching for when the number of query parameters > 100
 */
@Log4j2
public class ClipsAPI extends AbstractPaginatedAPI<Clip, Clips> {

    private static final DateTimeFormatter RFC3339_DTF = DateTimeFormatter.ISO_INSTANT;

    @Getter
    private ClipsEndpoint clipsEndpoint;

    /**
     * Construct the Helix Clips API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Clips API.
     */
    public ClipsAPI(final Helix helix) {
        super(helix);
        this.clipsEndpoint = helix.getRetrofit().create(ClipsEndpoint.class);
    }

    /**
     * Gets a list of Clips for the specified broadcaster ID.
     * @param broadcasterId Broadcaster's user ID.
     * @return List of Clips for the specified broadcaster ID.
     */
    public List<Clip> getClipsForBroadcaster(final String broadcasterId) {
        return getClipsForBroadcaster(broadcasterId, 0);
    }

    /**
     * Gets a list of Clips for the specified broadcaster ID, up to the maximum specified in {@code limit}.
     * @param broadcasterId Broadcaster's user ID.
     * @param limit Maximum number of results to return.
     * @return List of Clips for the specified broadcaster ID, up to the maximum specified in {@code limit}.
     */
    public List<Clip> getClipsForBroadcaster(final String broadcasterId, final long limit) {
        return performPagination(getCallParams(null, broadcasterId, null, null, null),
            (double) limit);
    }

    /**
     * Gets a list of Clips for the specified Game ID, up to the maximum specified in {@code limit}.
     * @param gameId ID of the Game
     * @param limit Maximum number of results to return.
     * @return List of Clips for the specified Game ID, up to the maximum specified in {@code limit}.
     */
    public List<Clip> getClipsForGame(final String gameId, final long limit) {
        return performPagination(getCallParams(null, null, gameId, null, null),
            (double) limit);
    }

    @SuppressWarnings("unchecked")
    protected Optional<Clips> innerCall(final Map<String, Object> callParams, final int first, final String cursor) {
        try {
            final List<String> ids = (List<String>) callParams.get("ids");
            final String broadcasterId = (String) callParams.get("broadcasterId");
            final String gameId = (String) callParams.get("gameId");
            final String startedAt = (String) callParams.get("startedAt");
            final String endedAt = (String) callParams.get("endedAt");
            final Call<Clips> call = clipsEndpoint.getClips(ids, broadcasterId, gameId, endedAt, startedAt, first, cursor);
            return executePaginatedCall(call);
        } catch (final ClassCastException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, String)}.
     */
    private Map<String, Object> getCallParams(final List<String> ids, final String broadcasterId, final String gameId,
                                              final ZonedDateTime startedAt, final ZonedDateTime endedAt) {
        final Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("broadcasterId", broadcasterId);
        map.put("gameId", gameId);
        map.put("startedAt", (startedAt == null) ? null : startedAt.format(RFC3339_DTF));
        map.put("endedAt", (endedAt == null) ? null : endedAt.format(RFC3339_DTF));
        return map;
    }
}
