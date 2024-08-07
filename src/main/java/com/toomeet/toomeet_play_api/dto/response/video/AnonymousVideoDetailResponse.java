/*
 *  AnonymousVideoDetailResponse
 *  @author: Minhhieuano
 *  @created 7/31/2024 7:29 PM
 * */

package com.toomeet.toomeet_play_api.dto.response.video;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AnonymousVideoDetailResponse {
    private String id;
    private String title;
    private String description;
    private String channelId;
    private boolean allowedComment;
    private boolean forKid;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private Integer viewCount;
    private String url;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
