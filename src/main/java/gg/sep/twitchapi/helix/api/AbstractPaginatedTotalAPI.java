package gg.sep.twitchapi.helix.api;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.model.AbstractPaginatedTotal;
import gg.sep.twitchapi.helix.model.HelixObject;

/**
 * Same implementation as {@link AbstractPaginatedAPI}, but also implements {@link gg.sep.twitchapi.helix.model.Total}.
 * @param <T> Type of the HelixObjects which are contained in the data field of the response.
 * @param <P> Type of the {@link gg.sep.twitchapi.helix.model.Paginated} actually returned by the API,
 *            which has <code>T</code> as its type parameter.
 */
public abstract class AbstractPaginatedTotalAPI<T extends HelixObject, P extends AbstractPaginatedTotal<T>>
    extends AbstractPaginatedAPI<T, P> {

    protected AbstractPaginatedTotalAPI(final Helix helix) {
        super(helix);
    }
}
