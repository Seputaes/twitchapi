package gg.sep.twitchapi.kraken.model;

/**
 * Represents a generic PUT body object to the Kraken API, the JSOn of which can be accessed from {@code field}.
 */
public interface KrakenPut {
    /**
     * Generic object on the put object.
     *
     * The exact name of the field when serialized can be denoted with Gson's
     * {@link com.google.gson.annotations.SerializedName} annotation.
     * @return Object which will be serialized in the PUT request body.
     */
    Object getField();
}
