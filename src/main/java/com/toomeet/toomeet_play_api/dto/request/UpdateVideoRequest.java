package com.toomeet.toomeet_play_api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toomeet.toomeet_play_api.enums.Visibility;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class UpdateVideoRequest {
    private Visibility visibility;
    private String title;
    private String description;
}
