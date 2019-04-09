package gg.sep.twitchapi.test.model;

import lombok.Getter;

/**
 * Implementation of a Paginated Data List API Response which also contains the total records for the query.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
public class DataListTotalPaginated<T extends APIObject> extends DataListPaginated<T> implements Total {

    @Getter private long total;
}
