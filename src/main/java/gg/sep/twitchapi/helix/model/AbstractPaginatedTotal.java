package gg.sep.twitchapi.helix.model;

import lombok.Getter;

/**
 * Combined abstract implementation of both the {@link Paginated} and {@link Total} interfaces.
 *
 * Implements the base Getter for the total field.
 * The pagination field is already implemented on {@link AbstractPaginated)}.
 * @param <T> Type of the HelixObjects which are contained in the data field of the response.
 */
@Getter
public abstract class AbstractPaginatedTotal<T extends HelixObject> extends AbstractPaginated<T> implements Total {
    private Long total;
}
