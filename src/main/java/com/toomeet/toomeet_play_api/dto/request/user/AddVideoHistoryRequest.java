package com.toomeet.toomeet_play_api.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddVideoHistoryRequest {
    @NotEmpty(message = "Video id can't empty or null")
    private String videoId;
}
