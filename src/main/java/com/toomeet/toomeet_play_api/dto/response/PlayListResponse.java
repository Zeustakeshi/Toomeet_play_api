package com.toomeet.toomeet_play_api.dto.response;

import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlaylistResponse {
    protected Visibility visibility;
    private String playlistId;
    private String name;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
