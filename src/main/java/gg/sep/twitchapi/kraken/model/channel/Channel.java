package gg.sep.twitchapi.kraken.model.channel;

import java.net.URL;
import java.time.ZonedDateTime;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractKrakenObject;
import gg.sep.twitchapi.kraken.model.KrakenPut;
import gg.sep.twitchapi.kraken.model.Puttable;

/**
 * Implements the Channel Kraken API object.
 * https://dev.twitch.tv/docs/v5/reference/channels/
 */
@Getter
@Builder
public class Channel extends AbstractKrakenObject implements Puttable {
    @SerializedName("_id")
    private Long id;

    @SerializedName("broadcaster_language")
    private String broadcasterLanguage;

    @SerializedName("created_at")
    private ZonedDateTime createdAt;

    @SerializedName("display_name")
    private String displayName;

    private String game;
    private String language;
    private URL logo;
    private Boolean mature;
    private String name;
    private Boolean partner;

    @SerializedName("profile_banner")
    private URL profileBanner;

    @SerializedName("profile_banner_background_color")
    private String profileBannerBackgroundColor;

    private String status;

    @SerializedName("updated_at")
    private ZonedDateTime updatedAt;

    private URL url;

    @SerializedName("video_banner")
    private URL videoBanner;

    private Long views;

    /**
     * Implements the KrakenPut object which will be serialized for updating channels.
     * https://dev.twitch.tv/docs/v5/reference/channels/#update-channel
     */
    @AllArgsConstructor
    public class ChannelPut implements KrakenPut {
        @Getter
        @SerializedName("channel")
        public Channel field;
    }

    /**
     * Constructs the PUT object to be serialized for channel updates.
     * @return Constructed Channel PUT object.
     */
    public ChannelPut getPut() {
        return new ChannelPut(this);
    }
}
