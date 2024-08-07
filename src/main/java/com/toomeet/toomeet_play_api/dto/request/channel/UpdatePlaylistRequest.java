package com.toomeet.toomeet_play_api.dto.request.channel;

import com.toomeet.toomeet_play_api.enums.Visibility;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePlaylistRequest {
    @NotEmpty
    @Size(min = 5, max = 100)
    private String name;

    @Size(min = 20, max = 15000)
    private String description;

    private Visibility visibility;
}
