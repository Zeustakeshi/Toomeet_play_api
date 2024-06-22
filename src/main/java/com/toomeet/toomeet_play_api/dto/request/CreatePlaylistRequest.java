package com.toomeet.toomeet_play_api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePlaylistRequest {
    @NotNull(message = "Playlist name can't be null or empty")
    @Size(min = 5, max = 100, message = "Playlist name must be at least 5 characters and less than 100 characters")
    private String name;

    @Size(max = 2000, message = "Playlist name must be less than 100 characters")
    private String description;
}
