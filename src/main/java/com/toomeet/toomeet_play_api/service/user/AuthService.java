package com.toomeet.toomeet_play_api.service.user;

import com.toomeet.toomeet_play_api.dto.request.CreateAccountRequest;
import com.toomeet.toomeet_play_api.dto.request.LoginRequest;
import com.toomeet.toomeet_play_api.dto.request.RefreshTokenRequest;
import com.toomeet.toomeet_play_api.dto.response.CreateAccountResponse;
import com.toomeet.toomeet_play_api.dto.response.TokenResponse;

public interface AuthService {
    CreateAccountResponse createAccountWithEmailAndPassword(CreateAccountRequest request);

    String verifyAccountConfirmation(String code);

    TokenResponse loginWithEmailAndPassword(LoginRequest request);

    TokenResponse refreshToken(RefreshTokenRequest request);
}
