package gg.sep.twitchapi.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import retrofit2.Invocation;

import gg.sep.twitchapi.TwitchAPIConfig;
import gg.sep.twitchapi.auth.Scope;
import gg.sep.twitchapi.auth.TokenFor;

/**
 * Retrofit/OkHttp3 request interceptor for applying API authentication headers.
 */
@Log4j2
public abstract class AbstractAuthInterceptor implements Interceptor {

    private static final String KEY_PREFIX = "twitchapi:oauth:";

    private final TwitchAPIConfig config;
    private final JedisPool jedisPool;

    /**
     * Construct the auth interceptor using the specified Jedis Pool for auth caching.
     * @param jedisPool JedisPool which contains the oauth keys.
     * @param config Twitch API configuration.
     */
    public AbstractAuthInterceptor(final JedisPool jedisPool, final TwitchAPIConfig config) {
        this.config = config;
        this.jedisPool = jedisPool;
    }

    /**
     * Applies the OAuth Authorization header to applicable Helix requests.
     * @param chain Okhttp3 request chain.
     * @return The response after modification..
     * @throws IOException Possible exception thrown by OkHttp3 on proceed. Bubble it up.
     */
    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Optional<String> oauthToken = getAuthToken(chain);
        final String token = oauthToken.orElse(config.getAppOauthToken());
        final Request request = chain.request().newBuilder()
            .addHeader("Authorization", getTokenPrefix() + " " + token)
            .build();
        return chain.proceed(request);
    }

    /**
     * Checks if the originating call requires authentication and returns the necessary auth token.
     *
     * Calls should be annotated with {@link Scope}, which declares the OAuth scope needed to
     * execute the API call successfully. Since we need a unique OAuth token per Twitch user which
     * has granted access to the bot, and because **most** of the authenticated calls will also have
     * the Twitch User's/Channel/etc ID as part of the request, we need to annotate that parameter with
     * {@link TokenFor}.
     *
     * This method finds any methods which have declared scopes AND gets the parameter value of the {@link TokenFor}
     * parameter. The scope and parameter value are then looked up in the database to see if we have record of them.
     * If so, it returns the necessary OAuth token.
     * @param chain Okhttp interceptor Chain;
     * @return OAuth token for the scope and user, if applicable.
     */
    private Optional<String> getAuthToken(final Chain chain) {
        try {
            final Invocation invocation = chain.request().tag(Invocation.class);
            if (invocation == null) {
                return Optional.empty();
            }

            final Method callMethod = invocation.method();
            final Scope scope = callMethod.getAnnotation(Scope.class);
            if (scope == null) {
                return Optional.empty();
            }

            final Optional<Parameter> tokenForParameter = Arrays.stream(callMethod.getParameters())
                .filter(p -> p.getAnnotation(TokenFor.class) != null)
                .findFirst();

            if (tokenForParameter.isPresent()) {
                final int paramIndex = Integer.parseInt(tokenForParameter.get()
                    .getName().replace("arg", ""));
                final String parameterValue = String.valueOf(invocation.arguments().get(paramIndex));
                return getAuthTokenForKey(parameterValue, scope.value());
            }
        } catch (final Exception e) {
            log.error(e);
            return Optional.empty();
        }
        return Optional.empty();
    }

    private Optional<String> getAuthTokenForKey(final String key, final String scope) {

        final List<String> neededScopes = Arrays.asList(scope.split("\\|"));

        final String redisKey = KEY_PREFIX + key;
        try (Jedis jedis = jedisPool.getResource()) {
            final Map<String, String> oauthMap = jedis.hgetAll(redisKey);
            if (oauthMap == null) {
                return Optional.empty();
            }
            // check if we have the scope necessary
            final List<String> scopes = Arrays.asList(oauthMap.getOrDefault("scopes", "").split("\\|"));
            final String accessToken = oauthMap.get("access_token");

            if (Collections.disjoint(scopes, neededScopes)) {
                log.error("Client does not have appropriate scope. key={}, scope={}", redisKey, scope);
                return Optional.empty();
            }
            return Optional.ofNullable(accessToken);
        }
    }

    protected abstract String getTokenPrefix();
}
