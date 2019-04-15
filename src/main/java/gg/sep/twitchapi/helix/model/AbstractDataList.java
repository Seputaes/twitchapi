package gg.sep.twitchapi.helix.model;

import java.util.List;

import lombok.Getter;

/**
 * Abstract implementation of {@link DataList}.
 *
 * Implements the base Getter for the data field.
 * @param <T> Type of the Helix Objects which are contained in the data field of the response.
 */
@Getter
public abstract class AbstractDataList<T extends HelixObject> implements DataList<T> {
    private List<T> data;
}
