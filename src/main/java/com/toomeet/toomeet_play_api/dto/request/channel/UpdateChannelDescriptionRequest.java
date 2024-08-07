package com.toomeet.toomeet_play_api.dto.request.channel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateChannelDescriptionRequest {
    @NotEmpty(message = "Channel description must not empty or null")
    @Size(min = 5, max = 15000)
    private String description;
}
