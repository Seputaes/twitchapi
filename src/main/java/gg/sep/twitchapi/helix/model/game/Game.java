package gg.sep.twitchapi.helix.model.game;

import java.net.URL;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the Game Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-games
 */
@Getter
public class Game extends AbstractHelixObject {
    private String id;
    private String name;
    @SerializedName("box_art_url")
    private URL boxArtUrl; // TODO: Template URL
}
