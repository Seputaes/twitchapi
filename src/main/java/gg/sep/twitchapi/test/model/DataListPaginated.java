package gg.sep.twitchapi.test.model;

import lombok.Getter;

/**
 * Variation of {@link DataList} which also supports Pagination.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
public class DataListPaginated<T extends APIObject> extends DataList<T>
    implements DataAPIObject<T>, Paginated {

    @Getter private Pagination pagination;
}
