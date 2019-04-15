package gg.sep.twitchapi.helix.model;

/**
 * Represents an object which contains a Total field, typically used in responses.
 */
public interface Total {
    /**
     * Total number of data items across the response(s).
     * @return Total number of data items across the response(s).
     */
    Long getTotal();
}
