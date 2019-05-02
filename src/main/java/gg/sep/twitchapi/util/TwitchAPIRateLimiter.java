package gg.sep.twitchapi.util;

import java.io.IOException;
import java.util.Optional;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Response;

import gg.sep.twitchapi.TwitchAPIConfig;

/**
 * Implements HTTP execution methods for the Twitch API, and handles throttling.
 */
@Log4j2
public final class TwitchAPIRateLimiter {

    private final RateLimiter rateLimiter;

    /**
     * Construct the rate limiter using the specified API configuration.
     * @param apiConfig Twitch API Configuration
     */
    public TwitchAPIRateLimiter(final TwitchAPIConfig apiConfig) {
        this.rateLimiter = RateLimiter.create(apiConfig.getApiRateLimit());
    }


    /**
     * Rate limits the call against the configures rate limiter.
     * @param call Retrofit call to execute.
     * @param <P> The type of the call's response.
     * @return An optional of the response if successful, or empty if error.
     * @throws IOException Thrown by Retrofit if the call fails for some IO reason.
     */
    public <P> Optional<P> getResponse(final Call<P> call) throws IOException {
        final Double waitTimeMs = this.rateLimiter.acquire() * 1000;
        final Response<P> response = call.execute();
        log.info("Twitch API | path={}, rateLimitWaitMs={}", call.request().url().encodedPath(), waitTimeMs);
        if (response.code() == 429) {
            log.warn("Rate limit hit");
            Waits.simpleSleep(200);
            return getResponse(call.clone());
        }
        return Optional.ofNullable(response.body());
    }
}
