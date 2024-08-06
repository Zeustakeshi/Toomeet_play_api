package com.toomeet.toomeet_play_api.dto.response.playlist;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.enums.Visibility;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
