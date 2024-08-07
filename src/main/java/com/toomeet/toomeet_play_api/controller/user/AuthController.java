package com.toomeet.toomeet_play_api.controller.user;

import com.toomeet.toomeet_play_api.dto.request.auth.*;
import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.service.user.AuthService;
import com.toomeet.toomeet_play_api.service.user.OAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OAuthService oAuthService;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<?>> createAccount(
            @RequestBody() @Valid CreateAccountRequest createAccountRequest) {

        ApiResponse<?> response =
                ApiResponse.success(authService.createAccountWithEmailAndPassword(createAccountRequest));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody() @Valid LoginRequest loginRequest) {
        ApiResponse<?> response = ApiResponse.success(authService.loginWithEmailAndPassword(loginRequest));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<ApiResponse<?>> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        ApiResponse<?> response = ApiResponse.success(authService.refreshToken(refreshTokenRequest));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/auth/verify-account")
    public ResponseEntity<ApiResponse<?>> verifyConfirmation(@RequestBody @Valid VerifyAccountRequest request) {
        ApiResponse<?> response = ApiResponse.success(authService.verifyAccountConfirmation(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/oauth/google")
    public ResponseEntity<ApiResponse<?>> getGoogleOAuthUrl() {
        ApiResponse<?> response = ApiResponse.success(oAuthService.getGoogleOAuthUrl());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/oauth/github")
    public ResponseEntity<ApiResponse<?>> getGithubOAuthUrl() {
        ApiResponse<?> response = ApiResponse.success(oAuthService.getGithubOAuthUrl());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/oauth/login/github")
    public ResponseEntity<ApiResponse<?>> loginWithGithub(@RequestBody @Valid OauthLoginRequest request) {
        ApiResponse<?> response = ApiResponse.success(oAuthService.loginWidthGithub(request.getCode()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/oauth/login/google")
    public ResponseEntity<ApiResponse<?>> loginWithGoogle(@RequestBody @Valid OauthLoginRequest request) {
        ApiResponse<?> response = ApiResponse.success(oAuthService.loginWithGoogle(request.getCode()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
