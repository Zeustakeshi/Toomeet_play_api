package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.response.AccountAuthenticationResponse;

public interface OAuthService {
    String getGoogleOAuthUrl();

    String getGithubOAuthUrl();

    AccountAuthenticationResponse loginWithGoogle(String code);

    AccountAuthenticationResponse loginWidthGithub(String code);
}
