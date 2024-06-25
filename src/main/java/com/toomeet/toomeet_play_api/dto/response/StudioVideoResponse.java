package com.toomeet.toomeet_play_api.dto.response;

import com.toomeet.toomeet_play_api.enums.Language;
import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StudioVideoResponse {
    private String title;
    private String description;
    private String videoId;
    private String thumbnail;
    private String url;
    private Long width;
    private Long height;
    private String format;
    private Visibility visibility;
    private Language language;
    private LocalDateTime localDateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
