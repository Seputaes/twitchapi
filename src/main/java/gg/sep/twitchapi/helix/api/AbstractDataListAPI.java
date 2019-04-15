package gg.sep.twitchapi.helix.api;

import java.io.IOException;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.model.DataList;
import gg.sep.twitchapi.helix.model.HelixObject;

/**
 * Abstract implementation of a Helix endpoint which returns a list of Helix Objects of type {@code T}.
 * @param <T> Type of the Helix Objects which are contained in the data field of the response.
 * @param <L> Type of the {@link DataList} actually returned by the API, which has {@code T} as its type parameter.
 */
@Log4j2
public abstract class AbstractDataListAPI<T extends HelixObject, L extends DataList<T>> extends HelixAPI<T> {

    protected AbstractDataListAPI(final Helix helix) {
        super(helix);
    }

    /**
     * Executes the API call and converts the response body into the {@link DataList} Helix Object.
     *
     * If there was an error or parsing was unsuccessful, the error will be logged and
     * empty instead will be returned.
     * @param call Retrofit call to execute.
     * @return Optional of type {@code L} which represents the {@link DataList} object,
     *         with a list containing type {@code T}.
     */
    protected Optional<L> executeDataListCall(final Call<L> call) {
        try {
            final L l = call.execute().body();
            if (l != null) {
                l.getData().forEach(d -> d.setHelix(this.getHelix()));
            }
            return Optional.ofNullable(l);
        } catch (final IOException e) {
            log.error(e);
            return Optional.empty();
        }
    }
}
