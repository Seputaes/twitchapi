package gg.sep.twitchapi.test.model;

import lombok.Getter;

/**
 * Model for the Pagination object which is present on Paginated API responses, which contains a cursor.
 */
public class Pagination implements APIObject {
    @Getter public String cursor;
}
