package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.CreateAccountRequest;
import com.toomeet.toomeet_play_api.dto.request.LoginRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.service.AuthService;
import com.toomeet.toomeet_play_api.service.OAuthService;
import jakarta.servlet.http.HttpServletRequest;
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
            @RequestBody() @Valid CreateAccountRequest createAccountRequest,
            HttpServletRequest request
    ) {

        ApiResponse<?> response = ApiResponse.success(
                request,
                authService.createAccountWithEmailAndPassword(createAccountRequest)
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<?>> login(
            @RequestBody() @Valid LoginRequest loginRequest,
            HttpServletRequest request
    ) {
        ApiResponse<?> response = ApiResponse.success(request, authService.loginWithEmailAndPassword(loginRequest));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/auth/verify-account")
    public ResponseEntity<ApiResponse<?>> verifyConfirmation(
            @RequestParam("code") String code,
            HttpServletRequest request
    ) {
        ApiResponse<?> response = ApiResponse.success(request, authService.verifyAccountConfirmation(code));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/oauth/google")
    public ResponseEntity<ApiResponse<?>> getGoogleOAuthUrl(
            HttpServletRequest request
    ) {
        ApiResponse<?> response = ApiResponse.success(request, oAuthService.getGoogleOAuthUrl());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/oauth/github")
    public ResponseEntity<ApiResponse<?>> getGithubOAuthUrl(
            HttpServletRequest request
    ) {
        ApiResponse<?> response = ApiResponse.success(request, oAuthService.getGithubOAuthUrl());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/oauth/login/github")
    public ResponseEntity<ApiResponse<?>> loginWithGithub(@RequestParam("code") String code, HttpServletRequest request) {
        ApiResponse<?> response = ApiResponse.success(request, oAuthService.loginWidthGithub(code));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/oauth/login/google")
    public ResponseEntity<ApiResponse<?>> loginWithGoogle(@RequestParam("code") String code, HttpServletRequest request) {
        ApiResponse<?> response = ApiResponse.success(request, oAuthService.loginWithGoogle(code));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
