package com.toomeet.toomeet_play_api.dto.response.channel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelAnalyticsResponse {
    private int subscribers;
    private int members;
    private int videos;
}
