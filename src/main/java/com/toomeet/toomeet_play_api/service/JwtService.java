package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.response.TokenResponse;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    Long getAccessTokenExpiresTime();

    Long getRefreshTokenExpiresTime();

    TokenResponse generateTokenPair(Authentication authentication);

    TokenResponse refreshToken(String refreshToken);
}
