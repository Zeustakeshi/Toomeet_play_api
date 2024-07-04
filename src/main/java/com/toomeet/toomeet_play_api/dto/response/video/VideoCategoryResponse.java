package com.toomeet.toomeet_play_api.dto.response.video;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoCategoryResponse {
    private String id;
    private String name;
    private String description;
}
