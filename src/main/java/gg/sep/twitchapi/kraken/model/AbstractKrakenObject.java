package gg.sep.twitchapi.kraken.model;

import lombok.Getter;
import lombok.Setter;

import gg.sep.twitchapi.kraken.Kraken;

/**
 * Abstract implementation of {@link KrakenObject}.
 *
 * Implements the base Getter and Setter for the Kraken reference.
 */
public abstract class AbstractKrakenObject implements KrakenObject {
    @Getter @Setter
    private transient Kraken kraken;
}
