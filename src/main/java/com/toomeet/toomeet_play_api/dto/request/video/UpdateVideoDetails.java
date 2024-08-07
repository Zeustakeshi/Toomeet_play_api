package com.toomeet.toomeet_play_api.dto.request.video;

import com.toomeet.toomeet_play_api.enums.Language;
import com.toomeet.toomeet_play_api.enums.Visibility;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class UpdateVideoDetails {
    @NotNull(message = "AllowedComment must be not null or empty")
    private boolean allowedComment;

    @NotNull(message = "Visibility must be not null or empty")
    private Visibility visibility;

    @NotNull(message = "VideoForKid must be not null or empty")
    private boolean videoForKid;

    @NotEmpty
    @Size(min = 1, max = 50)
    private Set<String> tags;

    @NotEmpty
    private String category;

    @NotNull(message = "Language must be not null or empty")
    private Language language;

    @NotNull(message = "RecordDate must be not null or empty")
    private LocalDateTime recordDate;
}
