package com.toomeet.toomeet_play_api.dto.response.video;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VideoDetailPublicResponse extends AnonymousVideoDetailResponse {
    private ChannelBasicInfoResponse channel;
    private boolean liked;
    private boolean disliked;
    private boolean shared;
}
