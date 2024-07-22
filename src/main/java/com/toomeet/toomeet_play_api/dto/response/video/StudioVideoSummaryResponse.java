package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StudioVideoSummaryResponse {
    private String id;
    private String title;
    private String description;
    private String thumbnail;
    private long viewCount;
    private long commendCount;
    private long likeCount;
    private long dislikeCount;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
