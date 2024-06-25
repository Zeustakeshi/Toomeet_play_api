package com.toomeet.toomeet_play_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddVideoToPlaylistRequest {
    @NotNull(message = "Missing videoId")
    private String videoId;
    @NotNull(message = "Missing playlistId")
    private String playlistId;
}
