package gg.sep.twitchapi.utils;

import java.io.IOException;
import java.util.Optional;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import gg.sep.twitchapi.TwitchAPIConfig;

/**
 * Implements HTTP execution methods for the Twitch API, and handles throttling.
 */
@Log4j2
public final class TwitchAPIRateLimiter {

    private final RateLimiter rateLimiter;
    private CloseableHttpClient httpClient = null;

    /**
     * Construct the rate limiter using the specified API configuration.
     * @param apiConfig Twitch API Configuration
     */
    public TwitchAPIRateLimiter(final TwitchAPIConfig apiConfig) {
        this.rateLimiter = RateLimiter.create(apiConfig.getApiRateLimit());
    }

    private void ensureHttpClient() {
        if (httpClient == null) {
            this.httpClient = HttpClients.custom().build();
        }
    }

    /**
     * Execute an HTTP request against the Twitch API, blocking until a rate limiter lock is acquired.
     * @param request HTTP Request.
     * @return HTTP Response, or empty if there was an API error.
     */
    public Optional<HttpResponse> execute(final HttpUriRequest request) {
        this.ensureHttpClient();

        final Double waitTimeMs = this.rateLimiter.acquire() * 1000;
        try {
            final HttpResponse response = httpClient.execute(request);
            log.info("Twitch API | path={}, rateLimitWaitMs={}", request.getURI().getPath(), waitTimeMs);

            if (response.getStatusLine().getStatusCode() == 429) {
                log.warn("Rate limit hit");
                Waits.simpleSleep(200);
                execute(request);
            }
            return Optional.of(response);
        } catch (final IOException e) {
            log.error("Error calling Twitch API. Request: {}", request);
            log.error(e);
        }
        return Optional.empty();
    }
}
