package gg.sep.twitchapi.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Demotes that a Retrofit {@link retrofit2.Call} requires the OAuth scope specified in {@code value}.
 *
 * Scopes: https://dev.twitch.tv/docs/authentication/#scopes
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    /**
     * Name of the scope required for authenticated access to the API.
     * Scopes: https://dev.twitch.tv/docs/authentication/#scopes
     * @return Name of the scope required for authenticated access to the API.
     */
    String value();
}
