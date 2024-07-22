package com.toomeet.toomeet_play_api.dto.response.video;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoInteractionResponse {
    private boolean liked;
    private boolean disliked;
    private boolean shared;
}
