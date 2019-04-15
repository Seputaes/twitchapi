package gg.sep.twitchapi.interceptor;

import java.io.IOException;

import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrofit/OkHttp3 request interceptor for applying global Helix request properties.
 *
 * Things added such as:
 *   - Client-ID Header
 *   - Accept Header (application/vnd.twitchtv.v5+json)
 *   - Content-Type Header for JSON.
 */
@AllArgsConstructor
public abstract class AbstractClientInterceptor implements Interceptor {

    private final String clientId;

    /**
     * Apply Client ID, Acccept, and Content Type headers to all Helix requests.
     * @param chain Okhttp3 request chain.
     * @return The response after modification..
     * @throws IOException Possible exception thrown by OkHttp3 on proceed. Bubble it up.
     */
    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request request = chain.request().newBuilder()
            .addHeader("Client-ID", clientId)
            .addHeader("Accept", "application/vnd.twitchtv.v5+json")
            .addHeader("Content-Type", "application/json")
            .build();
        return chain.proceed(request);
    }
}
