package com.toomeet.toomeet_play_api.dto.video;

import com.toomeet.toomeet_play_api.entity.Channel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VideoDetailPublicDto {
    private String id;
    private String title;
    private String description;
    private boolean allowedComment;
    private boolean forKid;
    private Channel channel;
    private long likeCount;
    private long dislikeCount;
    private long commentCount;
    private long viewCount;

    private String url;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
