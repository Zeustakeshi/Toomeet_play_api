package com.toomeet.toomeet_play_api.dto.response.channel;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ChannelBasicInfoResponse {
    private String id;
    private String name;
    private String avatar;
}
