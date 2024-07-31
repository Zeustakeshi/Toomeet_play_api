/*
 *  AnonymousVideoDetailResponse
 *  @author: Minhhieuano
 *  @created 7/31/2024 7:29 PM
 * */


package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnonymousVideoDetailResponse {
    private String id;
    private String title;
    private String description;
    private boolean allowedComment;
    private boolean forKid;
    private ChannelBasicInfoResponse channel;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private Integer viewCount;
    private String url;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
