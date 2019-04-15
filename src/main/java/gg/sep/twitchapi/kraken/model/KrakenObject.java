package gg.sep.twitchapi.kraken.model;

import gg.sep.twitchapi.kraken.Kraken;

/**
 * Represents any one of the Object responses from the Twitch Kraken API.
 *
 * Used so we can infer about the type of our API models and assign a reference
 * to the Kraken instance. This is useful for writing KrakenObject subclass methods
 * which themselves can call back into the API, eg, checking if the broadcaster
 * of a Stream is a partner.
 */
public interface KrakenObject {

    /**
     * Retrieves a reference to the Kraken API which was used to generate this object,
     * so that we can call back into it from object methods.
     * @return Reference to the Kraken API.
     */
    Kraken getKraken();

    /**
     * Sets a reference to the Kraken API which was used to generate this method.
     * @param kraken Reference to the Kraken API.
     */
    void setKraken(Kraken kraken);
}
