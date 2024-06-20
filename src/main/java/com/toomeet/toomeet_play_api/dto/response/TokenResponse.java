package com.toomeet.toomeet_play_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TokenResponse {
    private String accessToken;
    private Instant accessTokenExpiresIn;
    private String refreshToken;
    private Instant refreshTokenExpiresIn;
}
