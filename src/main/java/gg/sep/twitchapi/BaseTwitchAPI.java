package gg.sep.twitchapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicHeader;

import gg.sep.twitchapi.utils.TwitchAPIRateLimiter;

/**
 * Abstract class for modeling each of the Twitch API endpoints.
 */
@Log4j2
public abstract class BaseTwitchAPI {
    private static final String BASE_URL = "https://api.twitch.tv/helix";

    private final Header authHeader;

    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private final URI uri = buildURI();

    @Getter(value = AccessLevel.PROTECTED)
    private final TwitchAPIConfig apiConfig;

    @Getter(value = AccessLevel.PROTECTED)
    private final TwitchAPIRateLimiter rateLimiter;

    protected abstract String getAPIPath();

    BaseTwitchAPI(final TwitchAPIConfig apiConfig, final TwitchAPIRateLimiter rateLimiter) {
        this.apiConfig = apiConfig;
        this.rateLimiter = rateLimiter;
        this.authHeader = new BasicHeader("Authorization", "Bearer " + apiConfig.getOauthToken());
    }

    private URI buildURI() {
        try {
            return new URIBuilder(BASE_URL + getAPIPath()).build();
        } catch (final URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected Optional<String> getJsonResponse(final List<NameValuePair> params) {
        try {
            final HttpUriRequest request = this.getRequest(params);
            final Optional<HttpResponse> response = this.rateLimiter.execute(request);

            if (response.isEmpty()) {
                log.error("Response was empty");
                return Optional.empty();
            }
            return Optional.of(new BasicResponseHandler().handleResponse(response.get()));
        } catch (final IOException e) {
            log.error(e);
        }
        return Optional.empty();
    }

    private HttpUriRequest getRequest(final List<NameValuePair> params) {
        return buildRequest(HttpGet.METHOD_NAME, params);
    }

    private HttpUriRequest postRequest(final List<NameValuePair> params) {
        return buildRequest(HttpPost.METHOD_NAME, params);
    }

    private HttpUriRequest buildRequest(final String method, final List<NameValuePair> params) {
        final RequestBuilder requestBuilder = RequestBuilder.create(method)
            .setHeader(authHeader)
            .setUri(getUri());

        for (final NameValuePair nvp : params) {
            requestBuilder.addParameter(nvp);
        }
        return requestBuilder.build();
    }
}
