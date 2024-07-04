package com.toomeet.toomeet_play_api.dto.request.video;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateVideoMetadataRequest {
    @NotEmpty(message = "Title must be not null or empty")
    @Size(min = 5, max = 2000, message = "Invalid title length. Length must be between (5, 2000) characters")
    private String title;

    @NotEmpty(message = "Description must be not null or empty")
    @Size(min = 5, max = 15000, message = "Invalid description length. Length must be between (5, 15000) characters")
    private String description;


}
