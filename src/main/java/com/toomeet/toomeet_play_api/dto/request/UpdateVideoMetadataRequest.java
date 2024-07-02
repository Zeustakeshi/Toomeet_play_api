package com.toomeet.toomeet_play_api.dto.request;

import com.toomeet.toomeet_play_api.enums.Language;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateVideoMetadataRequest {
    @NotEmpty(message = "Title must be not null or empty")
    @Size(min = 5, max = 2000, message = "Invalid title length. Length must be between (5, 2000) characters")
    private String title;

    @NotEmpty(message = "Description must be not null or empty")
    @Size(min = 5, max = 15000, message = "Invalid description length. Length must be between (5, 15000) characters")
    private String description;

    @NotNull(message = "Language must be not null or empty")
    private Language language;

    @NotNull(message = "RecordeDate must be not null or empty")
    private LocalDateTime recordeDate;
}
