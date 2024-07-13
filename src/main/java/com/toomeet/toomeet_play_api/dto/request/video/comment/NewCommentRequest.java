package com.toomeet.toomeet_play_api.dto.request.video.comment;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCommentRequest {
    @Size(min = 10, max = 15000)
    @NotNull(message = "Content can't be null")
    private String content;
    
    private String parentId;
}
