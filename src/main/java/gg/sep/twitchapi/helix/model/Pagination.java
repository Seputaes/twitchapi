package gg.sep.twitchapi.helix.model;

import lombok.Getter;

/**
 * Represents the Pagination item which is contained on {@link Paginated} API responses.
 *
 * The Pagination item contains a cursor which can be used in subsequent API calls
 * to get paginated results for large data lists.
 */
@Getter
public class Pagination {
    private String cursor;
}
