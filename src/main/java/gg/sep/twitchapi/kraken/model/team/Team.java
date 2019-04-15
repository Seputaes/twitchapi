package gg.sep.twitchapi.kraken.model.team;

import java.net.URL;
import java.time.ZonedDateTime;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractKrakenObject;

/**
 * Implements the Team Kraken API object.
 * https://dev.twitch.tv/docs/v5/reference/teams/
 */
@Getter
public class Team extends AbstractKrakenObject {
    @SerializedName("_id")
    private Long id;

    private URL background;
    private URL banner;

    @SerializedName("created_at")
    private ZonedDateTime createdAt;

    @SerializedName("display_name")
    private String displayName;

    private String info;
    private URL logo;
    private String name;

    @SerializedName("updated_at")
    private ZonedDateTime updatedAt;
}
