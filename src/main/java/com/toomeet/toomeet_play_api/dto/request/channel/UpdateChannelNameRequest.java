package com.toomeet.toomeet_play_api.dto.request.channel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateChannelNameRequest {
    @NotEmpty(message = "Channel name must not empty or null")
    @Size(min = 5, max = 200)
    private String name;
}
