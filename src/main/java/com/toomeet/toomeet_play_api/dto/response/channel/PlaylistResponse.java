package com.toomeet.toomeet_play_api.dto.response.channel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistResponse {
    private String id;
    private String name;
    private String description;
    private ChannelGeneralResponse owner;
    private int totalVideo;
}
