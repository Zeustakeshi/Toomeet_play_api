package com.toomeet.toomeet_play_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountAuthenticationResponse {
    private UserAuthenticationResponse user;
    private TokenResponse tokens;
}
