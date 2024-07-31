package com.toomeet.toomeet_play_api.dto.response.video;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVideoDetailResponse extends AnonymousVideoDetailResponse {
    private boolean liked;
    private boolean disliked;
    private boolean shared;
}
