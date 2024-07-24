package com.toomeet.toomeet_play_api.dto.response.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserOverviewResponse {
    private String name;
    private String avatar;
    private String id;
}
