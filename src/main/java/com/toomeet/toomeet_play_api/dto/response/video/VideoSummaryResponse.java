package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.enums.Visibility;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoSummaryResponse {
    private String id;
    private String title;
    private String description;
    private String thumbnail;
    private long viewCount;
    private long commentCount;
    private long likeCount;
    private long dislikeCount;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
