package com.toomeet.toomeet_play_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoCategoryResponse {
    private String name;
    private String description;
}
