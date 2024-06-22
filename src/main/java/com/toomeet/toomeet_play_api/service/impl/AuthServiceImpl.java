package com.toomeet.toomeet_play_api.service.impl;

import com.google.gson.Gson;
import com.toomeet.toomeet_play_api.domain.account.AccountConfirmation;
import com.toomeet.toomeet_play_api.dto.request.CreateAccountRequest;
import com.toomeet.toomeet_play_api.dto.request.LoginRequest;
import com.toomeet.toomeet_play_api.dto.request.RefreshTokenRequest;
import com.toomeet.toomeet_play_api.dto.response.AccountAuthenticationResponse;
import com.toomeet.toomeet_play_api.dto.response.CreateAccountResponse;
import com.toomeet.toomeet_play_api.dto.response.TokenResponse;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.Authority;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.event.EmailVerifyAccountEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.UserMapper;
import com.toomeet.toomeet_play_api.service.AuthService;
import com.toomeet.toomeet_play_api.service.JwtService;
import com.toomeet.toomeet_play_api.service.MailService;
import com.toomeet.toomeet_play_api.service.UserService;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserService userService;
    private final JwtService jwtService;
    private final Jedis jedis;
    private final PasswordEncoder passwordEncoder;
    private final Gson gson;
    private final MailService mailService;
    private final ApplicationEventPublisher publisher;
    private final UserMapper userMapper;


    @Override
    public CreateAccountResponse createAccountWithEmailAndPassword(CreateAccountRequest request) {

        if (userService.existedByEmail(request.getEmail())) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .authorities(new HashSet<>())
                .isVerified(false)
                .build();

        AccountConfirmation accountConfirmation = AccountConfirmation.builder()
                .user(user)
                .build();


        int confirmationExpiresTime = 24;

        String confirmationJson = gson.toJson(accountConfirmation);

        jedis.setex(
                accountConfirmation.getCode(),
                TimeUnit.HOURS.toSeconds(confirmationExpiresTime),
                confirmationJson
        );

        String username = StringUtils.defaultIfEmpty(user.getFullName(), user.getEmail());

        publisher.publishEvent(EmailVerifyAccountEvent.builder()
                .code(accountConfirmation.getCode())
                .email(user.getEmail())
                .name(username)
                .build());


        return CreateAccountResponse.builder()
                .message("Your account has been created. Please check your email to verify your new account.")
                .build();

    }

    @Override
    public String verifyAccountConfirmation(String code) {

        String confirmationJson = Optional.ofNullable(jedis.get(code))
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_CONFIRMATION_CODE));

        jedis.del(code);

        AccountConfirmation confirmation = gson.fromJson(confirmationJson, AccountConfirmation.class);

        User user = confirmation.getUser();

        user.addAllAuthority(Set.of(Authority.NORMAL_USER));
        user.setVerified(true);

        userService.saveUser(user);

        return "Your account has been verified. Please login !";
    }

    @Override
    public AccountAuthenticationResponse loginWithEmailAndPassword(LoginRequest request) {

        User user = Optional.ofNullable(userService.getUserByEmail(request.getEmail()))
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_CREDENTIAL));

        boolean isInValidCredential = user.getPassword() == null || !passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (isInValidCredential) throw new ApiException(ErrorCode.INVALID_CREDENTIAL);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        TokenResponse tokens = jwtService.generateTokenPair(authentication);

        return AccountAuthenticationResponse.builder()
                .user(userMapper.toUserAuthenticationResponse(user))
                .tokens(tokens)
                .build();
    }


    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return jwtService.refreshToken(request.getToken());
    }
}
