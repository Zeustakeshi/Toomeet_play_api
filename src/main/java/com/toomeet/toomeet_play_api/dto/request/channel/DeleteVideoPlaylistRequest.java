package com.toomeet.toomeet_play_api.dto.request.channel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeleteVideoPlaylistRequest {
    @NotEmpty
    private String videoId;
}
