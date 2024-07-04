package com.toomeet.toomeet_play_api.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadChannelAvatarEvent {
    private byte[] avatar;
    private String channelId;
    private String userId;
}
