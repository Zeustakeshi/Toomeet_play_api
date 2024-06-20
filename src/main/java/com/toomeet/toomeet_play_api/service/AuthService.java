package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.CreateAccountRequest;
import com.toomeet.toomeet_play_api.dto.request.LoginRequest;
import com.toomeet.toomeet_play_api.dto.response.AccountAuthenticationResponse;
import com.toomeet.toomeet_play_api.dto.response.CreateAccountResponse;

public interface AuthService {
    CreateAccountResponse createAccountWithEmailAndPassword(CreateAccountRequest request);

    String verifyAccountConfirmation(String code);

    AccountAuthenticationResponse loginWithEmailAndPassword(LoginRequest loginRequest);
}
