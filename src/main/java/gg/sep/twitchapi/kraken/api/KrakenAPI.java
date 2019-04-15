package gg.sep.twitchapi.kraken.api;

import java.io.IOException;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.kraken.model.KrakenObject;

/**
 * Abstract implementation of a Kraken endpoint which will return a single Kraken Object of type {@code T}.
 * @param <T> Type of the single Kraken Object which is the body of the response.
 */
@Log4j2
public abstract class KrakenAPI<T extends KrakenObject> {

    @Getter(AccessLevel.PROTECTED)
    private Kraken kraken;

    protected KrakenAPI(final Kraken kraken) {
        this.kraken = kraken;
    }

    /**
     * Executes the API call and converts the response body into the Kraken Object.
     *
     * If there was an error or parsing was unsuccessful, the error will be logged and
     * empty instead will be returned.
     * @param call Retrofit call to execute.
     * @return Optional of type <code>T</code>, which represents the Kraken Object.
     */
    protected Optional<T> executeCall(final Call<T> call) {
        try {
            final T t = call.execute().body();
            if (t != null) {
                t.setKraken(kraken);
            }
            return Optional.ofNullable(t);
        } catch (final IOException e) {
            log.error(e);
            return Optional.empty();
        }
    }
}
