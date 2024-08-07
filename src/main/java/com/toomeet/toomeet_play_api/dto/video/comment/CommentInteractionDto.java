package com.toomeet.toomeet_play_api.dto.video.comment;

import lombok.Data;

@Data
public class CommentInteractionDto {
    private boolean liked;
    private boolean disliked;
}
