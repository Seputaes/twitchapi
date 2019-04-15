package gg.sep.twitchapi.kraken.api;


import java.io.IOException;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.kraken.model.ItemList;
import gg.sep.twitchapi.kraken.model.KrakenObject;

/**
 * Abstract implementation of a Kraken endpoint which returns a list of Helix Objects of type {@code T}.
 * @param <T> Type of the Kraken Objects which are contained in the items field of the response.
 * @param <L> Type of the {@link ItemList} actually returned by the API, which has {@code T} as its type parameter.
 */
@Log4j2
public abstract class AbstractItemListAPI<T extends KrakenObject, L extends ItemList<T>> extends KrakenAPI<T> {

    protected AbstractItemListAPI(final Kraken kraken) {
        super(kraken);
    }

    /**
     * Executes the API call and converts the response body into the {@link ItemList} Kraken Object.
     *
     * If there was an error or parsing was unsuccessful, the error will be logged and
     * empty instead will be returned.
     * @param call Retrofit call to execute.
     * @return Optional of type {@code L}, which represents the {@link ItemList} object,
     *         with a list containing type {@code T}.
     */
    protected Optional<L> executeItemListCall(final Call<L> call) {
        try {
            final L l = call.execute().body();
            if (l != null) {
                l.getItems().forEach(d -> d.setKraken(this.getKraken()));
            }
            return Optional.ofNullable(l);
        } catch (final IOException e) {
            log.error(e);
            return Optional.empty();
        }
    }
}
