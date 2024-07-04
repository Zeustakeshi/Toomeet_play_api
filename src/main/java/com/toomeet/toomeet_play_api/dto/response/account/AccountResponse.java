package com.toomeet.toomeet_play_api.dto.response.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private String name;
    private String image;
    private String email;
}
