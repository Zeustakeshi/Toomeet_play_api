package com.toomeet.toomeet_play_api.dto.video.comment;

import com.toomeet.toomeet_play_api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDetailDto {
    private String id;
    private String content;
    private long totalReplies;
    private long totalLikes;
    private long totalDislikes;
    private User owner;

    private boolean liked;
    private boolean disliked;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
