package com.toomeet.toomeet_play_api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateChannelRequest {

    @NotEmpty(message = "Name can not be empty or null")
    private String name;

    @NotEmpty(message = "Description can not be empty or null")
    private String description;
}
