package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelGeneralResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoWatchResponse {
    private String id;
    private String title;
    private String description;
    private boolean allowedComment;
    private boolean forKid;

    private ChannelGeneralResponse channel;

    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private Integer viewCount;

    private String url;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
