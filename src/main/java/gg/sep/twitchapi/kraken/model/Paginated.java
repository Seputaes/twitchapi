package gg.sep.twitchapi.kraken.model;

/**
 * Represents an object with the same structure as {@link ItemList}, but also supports pagination.
 * @param <T> Type of the Kraken Objects which are contained in the items field of the response.
 */
public interface Paginated<T extends KrakenObject> extends ItemList<T> {
    /**
     * Returns the total number of items across the paginated response(s).
     *
     * This field is used for determining how many requests need to be made to get all records.
     * @return Total number of items across the response(s).
     */
    Long getTotal();
}
