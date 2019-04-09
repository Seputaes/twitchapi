package gg.sep.twitchapi.test.model;

import java.util.List;

/**
 * Represents a Twitch API response object which contains a Data collection.
 * @param <T> Type of the object contained within the Data collection parameter.
 */
public interface DataAPIObject<T extends APIObject> extends APIObject {

    /**
     * List of {@link T} objects.
     * @return List of the objects contained in thiS API response.
     */
    List<T> getData();
}
