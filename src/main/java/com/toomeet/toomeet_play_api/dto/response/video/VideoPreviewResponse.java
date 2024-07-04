package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelSummaryResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoPreviewResponse {
    private String id;
    private String title;
    private ChannelSummaryResponse channel;
    private String thumbnail;
    private Integer viewCount;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
