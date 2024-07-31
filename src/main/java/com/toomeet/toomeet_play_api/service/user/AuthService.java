package com.toomeet.toomeet_play_api.service.user;

import com.toomeet.toomeet_play_api.dto.request.auth.CreateAccountRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.LoginRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.RefreshTokenRequest;
import com.toomeet.toomeet_play_api.dto.response.account.TokenResponse;

public interface AuthService {
    String createAccountWithEmailAndPassword(CreateAccountRequest request);

    String verifyAccountConfirmation(String code);

    TokenResponse loginWithEmailAndPassword(LoginRequest request);

    TokenResponse refreshToken(RefreshTokenRequest request);
}
