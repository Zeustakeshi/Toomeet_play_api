package com.toomeet.toomeet_play_api.dto.response.video.comment;

import lombok.Data;

@Data
public class CommentReactionCountResponse {
    private long like;
    private long dislike;
}
