package gg.sep.twitchapi.helix.api.tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import retrofit2.Call;

import gg.sep.twitchapi.helix.Helix;
import gg.sep.twitchapi.helix.api.AbstractPaginatedAPI;
import gg.sep.twitchapi.helix.endpoint.TagsEndpoint;
import gg.sep.twitchapi.helix.model.tag.Tag;
import gg.sep.twitchapi.helix.model.tag.Tags;

/**
 * Implementation of the Twitch Helix Tags API.
 * Reference: https://dev.twitch.tv/docs/api/reference/#get-stream-tags
 *
 * TODO: Implement batching for when the number of query parameters > 100
 */
@Log4j2
public class TagsAPI extends AbstractPaginatedAPI<Tag, Tags> {

    @Getter
    private TagsEndpoint tagsEndpoint;

    /**
     * Construct the Helix Tags API with a reference to the Helix API instance.
     * @param helix Helix API instance to be used for the User Tags API.
     */
    public TagsAPI(final Helix helix) {
        super(helix);
        this.tagsEndpoint = helix.getRetrofit().create(TagsEndpoint.class);
    }

    /**
     * Gets a specific Tag by its ID.
     * @param tagId ID of the tag.
     * @return Optional of the tag. If a matching tag is not found, returns empty.
     */
    public Optional<Tag> getTagById(final String tagId) {
        final List<Tag> tags = getTags(Collections.singletonList(tagId), 0);
        return (tags.isEmpty()) ? Optional.empty() : Optional.of(tags.get(0));
    }

    /**
     * Gets a list of Tags for the specified Tag IDs.
     * @param tagIds List of Tag IDs
     * @return List of Tags for the specified Tag IDs. Tag IDs not found will not be included in the list.
     */
    public List<Tag> getTagsById(final List<String> tagIds) {
        return getTags(tagIds, 0);
    }

    /**
     * Gets a list of Tags for the specified Tag IDs, up to the maximum specified in {@code limit}.
     * @param tagIds List of Tag IDs.
     * @param limit Maximum number of results to return.
     * @return list of Tags for the specified Tag IDs, up to the maximum specified in {@code limit}.
     */
    public List<Tag> getTags(final List<String> tagIds, final long limit) {
        return performPagination(getCallParams(tagIds), (double) limit);
    }

    /**
     * Gets all Tags, up to the maximum specified in {@code limit}.
     * @param limit Maximum number of results to return. Set to 0 for unlimited.
     * @return All tags, upt to the maximum number specified n {@code limit}
     */
    public List<Tag> getTags(final int limit) {
        return getTags(null, (long) limit);
    }

    /**
     * Gets a list of tags currently on the specified broadcaster's Stream.
     * @param broadcasterId Broadcaster/Channel/User ID for which to get current Stream Tags.
     * @return List of tags currently on the specified broadcaster's Stream.
     */
    public List<Tag> getTagsForStream(final String broadcasterId) {
        return getHelix().getStreamsAPI().getStreamTagsAPI().getTagsForStream(broadcasterId);
    }

    @SuppressWarnings("unchecked")
    protected Optional<Tags> innerCall(final Map<String, Object> callParams, final int first, final String cursor) {
        try {
            final List<String> tagIds = (List<String>) callParams.get("tagIds");
            final Call<Tags> call = tagsEndpoint.getTags(tagIds, first, cursor);
            return executePaginatedCall(call);
        } catch (final ClassCastException | NullPointerException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    /**
     * Shorthand method for building the callParams map needed by {@link #innerCall(Map, int, String)}.
     */
    private Map<String, Object> getCallParams(final List<String> tagIds) {
        return Collections.singletonMap("tagIds", tagIds);
    }
}
