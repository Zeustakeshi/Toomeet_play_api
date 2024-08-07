package com.toomeet.toomeet_play_api.controller.user;

import com.toomeet.toomeet_play_api.dto.request.auth.CreateAccountRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.LoginRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.RefreshTokenRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.VerifyAccountRequest;
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
    public ResponseEntity<ApiResponse<?>> verifyConfirmation(
            @RequestBody @Valid VerifyAccountRequest request
    ) {
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

    @GetMapping("/oauth/login/github")
    public ResponseEntity<ApiResponse<?>> loginWithGithub(@RequestParam("code") String code) {
        ApiResponse<?> response = ApiResponse.success(oAuthService.loginWidthGithub(code));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/oauth/login/google")
    public ResponseEntity<ApiResponse<?>> loginWithGoogle(@RequestParam("code") String code) {
        ApiResponse<?> response = ApiResponse.success(oAuthService.loginWithGoogle(code));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
