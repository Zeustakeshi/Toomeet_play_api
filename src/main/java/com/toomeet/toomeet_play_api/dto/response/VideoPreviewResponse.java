package com.toomeet.toomeet_play_api.dto.response;

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
