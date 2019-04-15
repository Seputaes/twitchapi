package gg.sep.twitchapi.kraken.model.channel;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.kraken.model.AbstractItemList;
import gg.sep.twitchapi.kraken.model.team.Team;

/**
 * Implements the Channel Teams Kraken API object.
 * https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-teams
 */
@Getter
public class ChannelTeams extends AbstractItemList<Team> {
    @SerializedName("teams")
    private List<Team> items;
}
