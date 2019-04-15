package gg.sep.twitchapi.helix.model;

import lombok.Getter;

/**
 * Abstract implementation of {@link Paginated}.
 *
 * Implements the base Getter for the pagination field.
 * @param <T> Type of the Helix Objects which are contained in the data field of the response.
 */
@Getter
public abstract class AbstractPaginated<T extends HelixObject> extends AbstractDataList<T> implements Paginated<T> {
    private Pagination pagination;
}
