package gg.sep.twitchapi.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for various methods which perform sleeps and retries.
 */
@UtilityClass
public class Waits {

    /**
     * Sleep for the specified milliseconds, and handle the Interrupted Excemption.
     * @param timeMs Number of milliseconds to sleep.
     */
    public static void simpleSleep(final int timeMs) {
        try {
            Thread.sleep(timeMs);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
