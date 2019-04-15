package gg.sep.twitchapi.kraken;

import java.net.URL;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import okhttp3.OkHttpClient;
import redis.clients.jedis.JedisPool;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import gg.sep.twitchapi.kraken.api.channels.ChannelsAPI;
import gg.sep.twitchapi.kraken.api.users.UsersAPI;
import gg.sep.twitchapi.kraken.interceptor.KrakenAuthInterceptor;
import gg.sep.twitchapi.kraken.interceptor.KrakenClientInterceptor;
import gg.sep.twitchapi.serializer.URLAdapter;
import gg.sep.twitchapi.serializer.ZonedDateTimeAdapter;

/**
 * Provides access to the Twitch Kraken API endpoints.
 * API Reference: https://dev.twitch.tv/docs/api/v5
 *
 * Basic access requires a valid Client ID. This can be obtained on the Twitch
 * Developer Dashboard: https://dev.twitch.tv/dashboard/apps
 *
 * TODO: In order to perform Authenticated calls, the client application
 *       must have also gone through the OAuth workflow steps to get
 *       individual access to a specific Twitch User's account.
 *       I still need to implement the portion that looks up whether an OAuth
 *       access token has been generated for an account, and the scopes it has
 *       been granted. Each authorized API endpoint will declare the scope
 *       it requires, and act accordingly.
 */
public class Kraken {

    private static final String BASE_URL = "https://api.twitch.tv/kraken/";

    @Getter
    private final Retrofit retrofit;

    @Getter
    private final JedisPool jedisPool;

    @Getter(lazy = true)
    private final ChannelsAPI channelsAPI = new ChannelsAPI(this);

    @Getter(lazy = true)
    private final UsersAPI usersAPI = new UsersAPI(this);

    /**
     * Construct the Kraken API wrapper for the specified API client ID.
     * @param jedisPool Jedis Pool to be used for caching calls.
     * @param clientId Client ID of the Twitch App.
     */
    public Kraken(final String clientId, final JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new KrakenClientInterceptor(clientId));
        httpClient.addInterceptor(new KrakenAuthInterceptor(jedisPool));
        this.retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(getKrakenGson()))
            .client(httpClient.build())
            .baseUrl(BASE_URL)
            .build();
    }

    /**
     * Constructs the Gson object with customer type adapters to be used for deserializing Kraken JSON responses.
     * @return Gson with customer type adapters for Kraken.
     */
    private Gson getKrakenGson() {
        return new GsonBuilder()
            .registerTypeAdapter(URL.class, new URLAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();

    }

}
