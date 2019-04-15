package gg.sep.twitchapi.kraken.model;

import java.util.List;

/**
 * Represents an object response from the Kraken API which contains a list of type {@code T}. objects.
 * @param <T> Type of the Kraken Objects which are contained in the items field of the response.
 */
public interface ItemList<T extends KrakenObject> {
    /**
     * Returns a list of items with type {@code T}.
     * @return List of items with type {@code T}.
     */
    List<T> getItems();
}
