package gg.sep.twitchapi.test.model;

/**
 * Interface for {@link DataListPaginated} API responses which contain totals.
 */
public interface Total {
    /**
     * Total number of records in the query, regardless of pagination.
     * @return Total number of records for the query.
     */
    long getTotal();
}
