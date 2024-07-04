package com.toomeet.toomeet_play_api.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadVideoThumbnailEvent {
    private byte[] thumbnail;
    private String videoId;
    private String userId;
}
