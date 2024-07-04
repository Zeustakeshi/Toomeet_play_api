package com.toomeet.toomeet_play_api.dto.response.video;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoSmallResponse {
    private String id;
    private String thumbnail;
}
