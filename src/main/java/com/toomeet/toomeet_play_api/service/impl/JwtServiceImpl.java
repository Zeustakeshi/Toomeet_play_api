package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.response.TokenResponse;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;
    @Value("${jwt.key.access_token.expireIn}")
    private Long accessTokenExpiresTime;
    @Value("${jwt.key.refresh_token.expireIn}")
    private Long refreshTokenExpiresTime;

    @Override
    public String generateAccessToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(user.getUserId())
                .issuedAt(now)
                .issuer("Toomeet Play")
                .claim("email", user.getEmail())
                .claim("scope", user.getAuthorities())
                .claim("type", "access_token")
                .expiresAt(now.plus(accessTokenExpiresTime, ChronoUnit.HOURS))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(user.getUserId())
                .issuedAt(now)
                .issuer("Toomeet Play")
                .claim("email", user.getEmail())
                .claim("scope", user.getAuthorities())
                .claim("type", "refresh")
                .expiresAt(now.plus(accessTokenExpiresTime, ChronoUnit.HOURS))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    @Override
    public Instant getAccessTokenExpiresTime() {
        Instant now = Instant.now();
        return now.plus(accessTokenExpiresTime, ChronoUnit.HOURS);
    }

    @Override
    public Instant getRefreshTokenExpiresTime() {
        Instant now = Instant.now();
        return now.plus(refreshTokenExpiresTime, ChronoUnit.HOURS);
    }


    @Override
    public TokenResponse generateTokenPair(Authentication authentication) {
        return TokenResponse.builder()
                .accessToken(generateAccessToken(authentication))
                .refreshToken(generateRefreshToken(authentication))
                .accessTokenExpiresIn(getAccessTokenExpiresTime())
                .refreshTokenExpiresIn(getRefreshTokenExpiresTime())
                .build();
    }
}
