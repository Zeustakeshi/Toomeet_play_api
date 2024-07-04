package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.enums.Language;
import com.toomeet.toomeet_play_api.enums.ResourceUploadStatus;
import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    private LocalDateTime recordeDate;
    private boolean allowedComment;
    private boolean forKid;
}
