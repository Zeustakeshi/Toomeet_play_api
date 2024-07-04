package com.toomeet.toomeet_play_api.service.user;

import com.toomeet.toomeet_play_api.dto.response.account.TokenResponse;

public interface OAuthService {
    String getGoogleOAuthUrl();

    String getGithubOAuthUrl();

    TokenResponse loginWithGoogle(String code);

    TokenResponse loginWidthGithub(String code);
}
