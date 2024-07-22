package com.toomeet.toomeet_play_api.dto.playlist;

import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PlaylistTotalVideoDto {
    private String id;
    private String name;
    private String description;
    private String thumbnail;
    private long totalVideo;
    private Channel channel;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
