package com.toomeet.toomeet_play_api.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteVideoResourceEvent {
    private String publicId;
}
