package gg.sep.twitchapi.kraken.model;

import java.util.List;

/**
 * Abstract implementation of {@link ItemList}.
 *
 * Denotes that subclasses must implement a getItems method,
 * which is annotated with {@link com.google.gson.annotations.SerializedName} for its specific name.
 * @param <T> Type of the Kraken Objects which are contained in the items field of the response.
 */
public abstract class AbstractItemList<T extends KrakenObject> implements ItemList<T> {
    /**
     * Gets the list of items of type {@code T} for this API response.
     *
     * Subclass implementations should annotate this field with
     * {@link com.google.gson.annotations.SerializedName} for its specific field name.
     * @return List of Kraken Objects of type {@code T}.
     */
    public abstract List<T> getItems();
}
