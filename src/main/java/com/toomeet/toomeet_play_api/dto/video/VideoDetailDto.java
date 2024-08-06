package com.toomeet.toomeet_play_api.dto.video;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoDetailDto {
    private String id;
    private String channelId;
    private String title;
    private String description;
    private boolean allowedComment;
    private boolean forKid;
    private long likeCount;
    private long dislikeCount;
    private long commentCount;
    private long viewCount;
    private boolean liked;
    private boolean disliked;
    private boolean shared;
    private String url;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
