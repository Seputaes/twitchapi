package gg.sep.twitchapi.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a {@link retrofit2.Call} method parameter as key to find the oauth token in the database.
 *
 * Used in combination with methods that are annotated with {@link Scope}.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenFor {
}
