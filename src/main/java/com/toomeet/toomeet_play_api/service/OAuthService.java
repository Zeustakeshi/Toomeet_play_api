package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.response.TokenResponse;

public interface OAuthService {
    String getGoogleOAuthUrl();

    String getGithubOAuthUrl();

    TokenResponse loginWithGoogle(String code);

    TokenResponse loginWidthGithub(String code);
}
