package gg.sep.twitchapi.kraken.model;

/**
 * Represents an API object which can be PUT back to the Kraken API.
 *
 * Each implementation should implement at {@code getPut} method which constructs that put object.
 */
public interface Puttable {
    /**
     * Constructs the PUT object necessary for the PUT, which will be serialized before the request.
     * @return PUT object which will be serialized before the request.
     */
    KrakenPut getPut();
}
