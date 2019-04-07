package gg.sep.twitchapi.model;

import lombok.Getter;

/**
 * Variation of {@link DataListResponse} which also supports Pagination.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
public class DataListResponsePaginated<T extends APIObject> extends DataListResponse<T>
    implements DataAPIObject<T>, Paginated {

    @Getter private Pagination pagination;
}
