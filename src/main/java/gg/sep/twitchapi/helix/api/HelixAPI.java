package gg.sep.twitchapi.helix.api;

import java.io.IOException;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.model.HelixObject;

/**
 * Abstract implementation of a Helix endpoint which will return a single Helix Object of type {@code T}.
 * @param <T> Type of the single Helix Object which is the body of the response.
 */
@Log4j2
public abstract class HelixAPI<T extends HelixObject> {

    @Getter(AccessLevel.PROTECTED)
    private Helix helix;

    HelixAPI(final Helix helix) {
        this.helix = helix;
    }

    /**
     * Executes the API call and converts the response body into the Helix Object.
     *
     * If there was an error or parsing was unsuccessful, the error will be logged and
     * empty instead will be returned.
     * @param call Retrofit call to execute.
     * @return Optional of type <code>T</code>, which represents the Helix Object.
     */
    protected Optional<T> executeCall(final Call<T> call) {
        try {
            return getHelix().getTwitchAPI().getRateLimiter().getResponse(call);
        } catch (final IOException e) {
            log.error(e);
            return Optional.empty();
        }
    }
}
