package com.toomeet.toomeet_play_api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateVideoTagRequest {

    @NotEmpty
    @Size(min = 1, max = 50)
    private Set<String> tags;
}
