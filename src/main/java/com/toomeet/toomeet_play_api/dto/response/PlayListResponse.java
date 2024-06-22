package com.toomeet.toomeet_play_api.dto.response;

import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayListResponse {
    protected Visibility visibility;
    private String playListId;
    private String name;
    private String videoCount;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
