package gg.sep.twitchapi.helix.api.streams;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.api.AbstractDataListAPI;
import gg.sep.twitchapi.helix.endpoint.StreamsEndpoint;
import gg.sep.twitchapi.helix.model.tag.Tag;
import gg.sep.twitchapi.helix.model.tag.Tags;

/**
 * Implementation of the Twitch Helix Stream Tags API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-stream-tags
 */
public class StreamTagsAPI extends AbstractDataListAPI<Tag, Tags> {

    @Getter
    private StreamsEndpoint streamsEndpoint;

    /**
     * Construct the Helix Stream Tags API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the Stream Tags API.
     */
    public StreamTagsAPI(final Helix helix) {
        super(helix);
        this.streamsEndpoint = helix.getStreamsAPI().getStreamsEndpoint();
    }

    /**
     * Gets a list of tags currently on the specified broadcaster's Stream.
     * @param broadcasterId Broadcaster/Channel/User ID for which to get current Stream Tags.
     * @return List of tags currently on the specified broadcaster's Stream.
     */
    public List<Tag> getTagsForStream(final String broadcasterId) {
        final Call<Tags> call = streamsEndpoint.getStreamTags(broadcasterId);
        final Optional<Tags> tags = executeDataListCall(call);
        return tags.map(Tags::getData).orElse(Collections.emptyList());
    }
}
