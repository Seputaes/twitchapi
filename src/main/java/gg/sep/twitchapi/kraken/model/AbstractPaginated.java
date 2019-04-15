package gg.sep.twitchapi.kraken.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Abstract implementation of {@link Paginated}.
 *
 * Implements the base Getter for the total field.
 * @param <T> Type of the Kraken Objects which are contained in the items field of the response.
 */
public abstract class AbstractPaginated<T extends KrakenObject> extends AbstractItemList<T> implements Paginated<T> {

    @Getter
    @SerializedName("_total")
    public Long total;
}
