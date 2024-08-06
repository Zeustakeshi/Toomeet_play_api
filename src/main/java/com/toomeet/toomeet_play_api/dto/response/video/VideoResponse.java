package com.toomeet.toomeet_play_api.dto.response.video;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.toomeet.toomeet_play_api.entity.video.Category;
import com.toomeet.toomeet_play_api.enums.Language;
import com.toomeet.toomeet_play_api.enums.ResourceUploadStatus;
import com.toomeet.toomeet_play_api.enums.Visibility;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoResponse {
    private String id;
    private String title;
    private String description;
    private String url;
    private String thumbnail;
    private Language language;
    private Visibility visibility;
    private Long width;
    private Long height;
    private ResourceUploadStatus uploadStatus;
    private LocalDateTime recordDate;
    private boolean allowedComment;
    private boolean forKid;

    @JsonProperty("category")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;
}
