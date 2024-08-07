package com.toomeet.toomeet_play_api.dto.request.video;

import com.toomeet.toomeet_play_api.enums.Visibility;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateVideoSettingRequest {
    @NotNull(message = "AllowedComment must be not null or empty")
    private boolean allowedComment;

    @NotNull(message = "Visibility must be not null or empty")
    private Visibility visibility;

    @NotNull(message = "VideoForKid must be not null or empty")
    private boolean videoForKid;
}
