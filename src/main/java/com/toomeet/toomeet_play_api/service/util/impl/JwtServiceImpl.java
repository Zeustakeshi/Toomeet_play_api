package com.toomeet.toomeet_play_api.service.util.impl;

import com.toomeet.toomeet_play_api.dto.response.account.TokenResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.util.JwtService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.key.access_token.expireIn}")
    private Long accessTokenExpiresTime;

    @Value("${jwt.key.refresh_token.expireIn}")
    private Long refreshTokenExpiresTime;

    @Override
    public String generateAccessToken(Authentication authentication) {

        Account account = (Account) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(account.getId())
                .issuedAt(now)
                .issuer("Toomeet Play")
                .claim("email", account.getEmail())
                .claim("scope", account.getAuthorities())
                .claim("type", "access_token")
                .expiresAt(now.plus(accessTokenExpiresTime, ChronoUnit.HOURS))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {

        Account account = (Account) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(account.getId())
                .issuedAt(now)
                .issuer("Toomeet Play")
                .claim("email", account.getEmail())
                .claim("scope", account.getAuthorities())
                .claim("type", "refresh")
                .expiresAt(now.plus(refreshTokenExpiresTime, ChronoUnit.HOURS))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    @Override
    public Long getAccessTokenExpiresTime() {
        Instant now = Instant.now();
        return now.plus(accessTokenExpiresTime, ChronoUnit.HOURS).toEpochMilli();
    }

    @Override
    public Long getRefreshTokenExpiresTime() {
        Instant now = Instant.now();
        return now.plus(refreshTokenExpiresTime, ChronoUnit.HOURS).toEpochMilli();
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

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        String userId = jwt.getSubject();
        Account user = (Account) userDetailsService.loadUserByUsername(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return generateTokenPair(authentication);
    }
}
