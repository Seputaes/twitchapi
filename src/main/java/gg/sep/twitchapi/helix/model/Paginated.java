package gg.sep.twitchapi.helix.model;

/**
 * Represents an object with the same structure as {@link DataList}, but also supports pagination on multiple requests.
 * @param <T> Type of the HelixObjects which are contained in the data field of the response.
 */
public interface Paginated<T extends HelixObject> extends DataList<T> {
    /**
     * Returns the Pagination HelixObject, which contains the cursor needed for creating paginated requests.
     * @return Returns the Pagination HelixObject, which contains the cursor needed for creating paginated requests.
     */
    Pagination getPagination();
}
