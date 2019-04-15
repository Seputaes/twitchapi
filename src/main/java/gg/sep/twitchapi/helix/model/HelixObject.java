package gg.sep.twitchapi.helix.model;

import gg.sep.twitchapi.helix.Helix;

/**
 * Represents any one of the Object responses from the Twitch Helix API.
 *
 * Used so we can infer the type of our API models and assign a reference
 * to the Helix instance. This is useful for writing HelixObject subclass methods
 * which themselves can call back into the API, eg, checking if the broadcaster
 * of a Stream is a Partner.
 */
public interface HelixObject {
    /**
     * Retrieves a reference to the Helix API which was used to generate this object,
     * so that we can call back into it from object methods.
     * @return Reference to the Helix API.
     */
    Helix getHelix();

    /**
     * Sets a reference to the Helix API which was used to generate this method.
     * @param helix Reference to the Helix API.
     */
    void setHelix(Helix helix);
}
