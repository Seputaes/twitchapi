package gg.sep.twitchapi.helix.model;

import lombok.Getter;
import lombok.Setter;

import gg.sep.twitchapi.helix.Helix;

/**
 * Abstract implementation of {@link HelixObject}.
 *
 * Implements the base Getter and Setter for the Helix reference.
 */
public abstract class AbstractHelixObject implements HelixObject {
    @Getter @Setter
    private transient Helix helix;
}
