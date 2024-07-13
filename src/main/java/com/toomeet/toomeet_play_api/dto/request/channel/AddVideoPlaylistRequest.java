package com.toomeet.toomeet_play_api.dto.request.channel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddVideoPlaylistRequest {
    @NotEmpty()
    private String videoId;
}
