package gg.sep.twitchapi.kraken.api.channels;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.kraken.Kraken;
import gg.sep.twitchapi.kraken.api.AbstractItemListAPI;
import gg.sep.twitchapi.kraken.endpoint.ChannelsEndpoint;
import gg.sep.twitchapi.kraken.model.channel.ChannelTeams;
import gg.sep.twitchapi.kraken.model.team.Team;

/**
 * Implementation of the Twitch Kraken Channel Teams API.
 * Reference: https://dev.twitch.tv/docs/v5/reference/channels/#get-channel-teams
 */
@Log4j2
public class ChannelTeamsAPI extends AbstractItemListAPI<Team, ChannelTeams> {

    private ChannelsEndpoint channelsEndpoint;

    /**
     * Construct the Kraken Channel Teams API with a reference to the Kraken API instance.
     * @param kraken Kraken API instance to be used for the Users Teams API.
     */
    public ChannelTeamsAPI(final Kraken kraken) {
        super(kraken);
        this.channelsEndpoint = kraken.getChannelsAPI().getChannelsEndpoint();
    }

    /**
     * Gets a list of teams for a channel.
     * @param channelId ID of the channel.
     * @return List of teams for a channel.
     */
    public List<Team> getTeams(final long channelId) {
        final Call<ChannelTeams> call = channelsEndpoint.getChannelTeams(channelId);
        final Optional<ChannelTeams> teams = executeItemListCall(call);
        return teams.map(ChannelTeams::getItems).orElse(Collections.emptyList());
    }
}
