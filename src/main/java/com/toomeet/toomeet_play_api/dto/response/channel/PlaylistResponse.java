package com.toomeet.toomeet_play_api.dto.response.channel;

import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PlaylistResponse {
    private String id;
    private String name;
    private String thumbnail;
    private String description;
    private ChannelBasicInfoResponse owner;
    private long totalVideo;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
