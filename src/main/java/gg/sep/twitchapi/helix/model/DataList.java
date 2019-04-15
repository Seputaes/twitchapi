package gg.sep.twitchapi.helix.model;

import java.util.List;

/**
 * Represents an object response from the Twitch Helix API which contains a list of type <code>T</code> objects.
 * @param <T> Type of the Helix Objects which are contained in the data field of the response.
 */
public interface DataList<T extends HelixObject> {
    /**
     * Returns a list of items with type <code>T</code>.
     * @return List of items with type <code>T</code>.
     */
    List<T> getData();
}
