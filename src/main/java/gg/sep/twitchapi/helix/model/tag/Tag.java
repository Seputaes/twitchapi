package gg.sep.twitchapi.helix.model.tag;

import java.util.Map;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import gg.sep.twitchapi.helix.model.AbstractHelixObject;

/**
 * Implements the Tag Helix API object.
 * https://dev.twitch.tv/docs/api/reference/#get-stream-tags
 */
@Getter
public class Tag extends AbstractHelixObject {

    @SerializedName("is_auto")
    private boolean isAuto;
    @SerializedName("localization_names")
    private Map<String, String> localizationNames;
    @SerializedName("localization_descriptions")
    private Map<String, String> localizationDescriptions;
    @SerializedName("tag_id")
    private String tagId;
}
