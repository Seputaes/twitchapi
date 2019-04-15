package gg.sep.twitchapi.helix;

import java.net.URL;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import okhttp3.OkHttpClient;
import redis.clients.jedis.JedisPool;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import gg.sep.twitchapi.helix.api.clips.ClipsAPI;
import gg.sep.twitchapi.helix.api.games.GamesAPI;
import gg.sep.twitchapi.helix.api.streams.StreamsAPI;
import gg.sep.twitchapi.helix.api.subscriptions.SubscriptionsAPI;
import gg.sep.twitchapi.helix.api.tags.TagsAPI;
import gg.sep.twitchapi.helix.api.users.UsersAPI;
import gg.sep.twitchapi.helix.api.videos.VideosAPI;
import gg.sep.twitchapi.helix.interceptor.HelixAuthInterceptor;
import gg.sep.twitchapi.helix.interceptor.HelixClientInterceptor;
import gg.sep.twitchapi.serializer.URLAdapter;
import gg.sep.twitchapi.serializer.ZonedDateTimeAdapter;

/**
 * Provides access to the Twitch Helix API endpoints.
 * API Reference: https://dev.twitch.tv/docs/api/reference/
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
public class Helix {

    private static final String BASE_URL = "https://api.twitch.tv/helix/";

    @Getter
    private final Retrofit retrofit;

    @Getter
    private final JedisPool jedisPool;

    @Getter(lazy = true)
    private final UsersAPI usersAPI = new UsersAPI(this);

    @Getter(lazy = true)
    private final GamesAPI gamesAPI = new GamesAPI(this);

    @Getter(lazy = true)
    private final StreamsAPI streamsAPI = new StreamsAPI(this);

    @Getter(lazy = true)
    private final TagsAPI tagsAPI = new TagsAPI(this);

    @Getter(lazy = true)
    private final ClipsAPI clipsAPI = new ClipsAPI(this);

    @Getter(lazy = true)
    private final VideosAPI videosAPI = new VideosAPI(this);

    @Getter(lazy = true)
    private final SubscriptionsAPI subscriptionsAPI = new SubscriptionsAPI(this);

    /**
     * Construct the Helix API wrapper for the specified API client ID.
     * @param clientId Client ID of the Twitch App.
     * @param jedisPool Jedis Pool to be used for caching calls.
     */
    public Helix(final String clientId, final JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HelixClientInterceptor(clientId));
        httpClient.addInterceptor(new HelixAuthInterceptor(jedisPool));
        this.retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(getHelixGson()))
            .client(httpClient.build())
            .baseUrl(BASE_URL)
            .build();
    }

    /**
     * Constructs the Gson object with customer type adapters to be used for deserializing Helix JSON responses.
     * @return Gson with customer type adapters for Helix.
     */
    private Gson getHelixGson() {
        return new GsonBuilder()
            .registerTypeAdapter(URL.class, new URLAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();
    }
}
