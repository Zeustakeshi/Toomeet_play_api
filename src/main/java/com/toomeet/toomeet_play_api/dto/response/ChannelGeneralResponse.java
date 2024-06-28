package com.toomeet.toomeet_play_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelGeneralResponse {
    private String channelId;
    private String name;
    private String background;
    private String avatar;
    private Integer memberCount;
    private Integer subscriberCount;
    private Long totalWatchTime;
    private Long viewCount;
    private Long videoCount;
}
