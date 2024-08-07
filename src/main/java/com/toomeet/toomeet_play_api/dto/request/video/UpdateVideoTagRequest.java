package com.toomeet.toomeet_play_api.dto.request.video;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;

@Data
public class UpdateVideoTagRequest {

    @NotEmpty
    @Size(min = 1, max = 50)
    private Set<String> tags;
}
