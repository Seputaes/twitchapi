package gg.sep.twitchapi.model;

/**
 * Interface for API endpoints which contain pagination.
 */
public interface Paginated extends APIObject {
    /**
     * Retrieves the Pagination object of of the API response object.
     * @return Pagination object, if present. Otherwise null.
     */
    Pagination getPagination();
}
