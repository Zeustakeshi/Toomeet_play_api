package com.toomeet.toomeet_play_api.dto.request.video;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateVideoCategoryRequest {
    @NotEmpty(message = "Category name must be not null or empty")
    @Size(min = 5, max = 2000, message = "Invalid length of category")
    private String name;

    @NotEmpty(message = "Category description must be not null or empty")
    @Size(min = 5, max = 15000, message = "Category description length valid between (5, 15000) characters")
    private String description;
}
