package com.toomeet.toomeet_play_api.dto.request;

import lombok.Data;

@Data
public class CreateVideoRequest {
    private String videoName;
    private String title;
    private String description;
}
