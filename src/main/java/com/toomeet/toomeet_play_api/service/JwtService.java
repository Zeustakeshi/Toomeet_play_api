package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.response.TokenResponse;
import org.springframework.security.core.Authentication;

import java.time.Instant;

public interface JwtService {
    String generateAccessToken(Authentication authentication);

    String generateRefreshToken(Authentication authentication);

    Instant getAccessTokenExpiresTime();

    Instant getRefreshTokenExpiresTime();

    TokenResponse generateTokenPair(Authentication authentication);
}
