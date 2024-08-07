package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class VideoNewsfeedResponse {
    private String id;
    private String title;
    private ChannelBasicInfoResponse channel;
    private String thumbnail;
    private long viewCount;
    private long likeCount;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
