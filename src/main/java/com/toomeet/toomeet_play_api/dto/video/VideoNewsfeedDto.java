package com.toomeet.toomeet_play_api.dto.video;

import com.toomeet.toomeet_play_api.entity.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VideoNewsfeedDto {
    private String id;
    private String title;
    private Channel channel;
    private String thumbnail;
    private long viewCount;
    private long likeCount;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
