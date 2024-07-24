package com.toomeet.toomeet_play_api.dto.response.video.comment;

import com.toomeet.toomeet_play_api.dto.response.user.UserOverviewResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private String id;
    private String content;
    private long totalReplies;
    private long totalLikes;
    private long totalDislikes;
    private UserOverviewResponse owner;

    @Builder.Default
    private boolean liked = false;
    @Builder.Default
    private boolean disliked = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
