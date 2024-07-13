package com.toomeet.toomeet_play_api.dto.response.video;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoReactionResponse {
    private Integer like;
    private Integer dislike;
}
