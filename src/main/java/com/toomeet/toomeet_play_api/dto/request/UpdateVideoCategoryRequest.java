package com.toomeet.toomeet_play_api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateVideoCategoryRequest {
    @NotEmpty
    private String category;
}
