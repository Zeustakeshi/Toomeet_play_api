package com.toomeet.toomeet_play_api.service.user.impl;

import com.google.gson.Gson;
import com.toomeet.toomeet_play_api.domain.account.AccountConfirmation;
import com.toomeet.toomeet_play_api.dto.request.auth.CreateAccountRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.LoginRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.RefreshTokenRequest;
import com.toomeet.toomeet_play_api.dto.request.auth.VerifyAccountRequest;
import com.toomeet.toomeet_play_api.dto.response.account.TokenResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.event.EmailVerifyAccountEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.service.user.AccountService;
import com.toomeet.toomeet_play_api.service.user.AuthService;
import com.toomeet.toomeet_play_api.service.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final Jedis jedis;
    private final PasswordEncoder passwordEncoder;
    private final Gson gson;
    private final ApplicationEventPublisher publisher;
    private final AccountService accountService;

    @Override
    public String createAccountWithEmailAndPassword(CreateAccountRequest request) {

        if (accountService.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Account account = Account.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getFullName())
                .authorities(new HashSet<>())
                .isVerified(false)
                .build();

        AccountConfirmation accountConfirmation = AccountConfirmation.builder()
                .email(account.getEmail())
                .password(account.getPassword())
                .name(account.getUsername())
                .build();

        int confirmationExpiresTime = 1;

        String confirmationJson = gson.toJson(accountConfirmation);

        jedis.setex(accountConfirmation.getCode(), TimeUnit.HOURS.toSeconds(confirmationExpiresTime), confirmationJson);

        String username = StringUtils.defaultIfEmpty(account.getName(), account.getEmail());

        publisher.publishEvent(EmailVerifyAccountEvent.builder()
                .code(accountConfirmation.getCode())
                .email(account.getEmail())
                .name(username)
                .build());

        return "Your account has been created. Please check your email to verify your new account.";
    }

    @Override
    public String verifyAccountConfirmation(VerifyAccountRequest request) {
        String confirmationJson = Optional.ofNullable(jedis.get(request.getCode()))
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_CONFIRMATION_CODE));

        AccountConfirmation confirmation = gson.fromJson(confirmationJson, AccountConfirmation.class);

        accountService.saveNewAccount(Account.builder()
                .name(confirmation.getName())
                .email(confirmation.getEmail())
                .password(confirmation.getPassword())
                .isVerified(true)
                .build());

        jedis.del(request.getCode());
        return "Your account has been verified. Please login !";
    }

    @Override
    public TokenResponse loginWithEmailAndPassword(LoginRequest request) {

        Account account = Optional.ofNullable(accountService.getAccountByEmail(request.getEmail()))
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_CREDENTIAL));

        boolean isInValidCredential =
                account.getPassword() == null || !passwordEncoder.matches(request.getPassword(), account.getPassword());

        if (isInValidCredential) throw new ApiException(ErrorCode.INVALID_CREDENTIAL);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());

        return jwtService.generateTokenPair(authentication);
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return jwtService.refreshToken(request.getRefreshToken());
    }
}
