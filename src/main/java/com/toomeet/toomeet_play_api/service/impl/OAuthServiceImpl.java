package com.toomeet.toomeet_play_api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toomeet.toomeet_play_api.dto.response.TokenResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.AccountMapper;
import com.toomeet.toomeet_play_api.service.AccountService;
import com.toomeet.toomeet_play_api.service.JwtService;
import com.toomeet.toomeet_play_api.service.OAuthService;
import com.toomeet.toomeet_play_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final JwtService jwtService;
    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final UserService userService;


    @Value("${spring.security.oauth2.url.google.oauth_url}")
    private String googleAuthenticationBaseUrl;
    @Value("${spring.security.oauth2.url.google.access_token_url}")
    private String googleAccessTokenUrl;
    @Value("${spring.security.oauth2.url.google.user_info_url}")
    private String googleUserInfoUrl;
    @Value("${spring.security.oauth2.url.github.oauth_url}")
    private String githubAuthenticationBaseUrl;
    @Value("${spring.security.oauth2.url.github.access_token_url}")
    private String githubAccessTokenUrl;
    @Value("${spring.security.oauth2.url.github.user_info_url}")
    private String githubUserInfoUrl;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubClientSecret;


    @Value("${frontend.oauth_callback}")
    private String callbackBaseUrl;

    @Override
    public String getGoogleOAuthUrl() {
        return getOAuthUrl(googleAuthenticationBaseUrl, googleClientId, "email profile", callbackBaseUrl + "/google");
    }

    @Override
    public String getGithubOAuthUrl() {
        return getOAuthUrl(githubAuthenticationBaseUrl, githubClientId, "user:email", callbackBaseUrl + "/github");
    }

    @Override
    public TokenResponse loginWithGoogle(String code) {
        try {
            ResponseEntity<String> responseEntity = getOAuthAccessToken(
                    googleAccessTokenUrl,
                    googleClientId,
                    googleClientSecret,
                    callbackBaseUrl + "/google",
                    code
            );

            String response = responseEntity.getBody();

            String accessToken = extractGoogleAccessToken(response);

            Account account = upsertOauthAccount(getGoogleUserInfo(accessToken));
            return getAccountAuthenticationResponse(account);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public TokenResponse loginWidthGithub(String code) {
        try {
            ResponseEntity<String> responseEntity = getOAuthAccessToken(
                    githubAccessTokenUrl,
                    githubClientId,
                    githubClientSecret,
                    callbackBaseUrl + "/github",
                    code
            );

            String response = responseEntity.getBody();

            assert response != null;

            String accessToken = extractGithubAccessToken(response);

            Account account = upsertOauthAccount(getGithubUserInfo(accessToken));
            return getAccountAuthenticationResponse(account);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private TokenResponse getAccountAuthenticationResponse(Account account) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
        return jwtService.generateTokenPair(authentication);
    }


    private Account upsertOauthAccount(Account account) {
        String email = account.getEmail();
        if (accountService.existsByEmail(email)) {
            account = accountService.getAccountByEmail(email);
        } else {
            account = accountService.saveNewAccount(account);
        }
        return account;
    }

    private Account getGithubUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);

        ResponseEntity<String> userInfoResponse = restTemplate.exchange(
                githubUserInfoUrl,
                HttpMethod.GET,
                userInfoRequest,
                String.class);


        String userInfo = userInfoResponse.getBody();
        JsonNode userInfoJson = mapper.readTree(userInfo);

        String email = getGithubUserEmail(accessToken);

        if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ErrorCode.OAUTH_LOAD_USER_INFO_ERROR);
        }

        return Account.builder()
                .name(userInfoJson.get("name").asText())
                .email(email)
                .image(userInfoJson.get("avatar_url").asText())
                .isVerified(true)
                .build();
    }

    private String getGithubUserEmail(String accessToken) throws JsonProcessingException {
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                githubUserInfoUrl + "/emails",
                HttpMethod.GET,
                userInfoRequest,
                String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ErrorCode.GITHUB_OAUTH_EMAIL_EXCEPTION);
        }

        String response = responseEntity.getBody();
        JsonNode emailsNode = mapper.readTree(response);

        for (JsonNode emailNode : emailsNode) {
            if (emailNode.has("primary") && emailNode.get("primary").asBoolean()) {
                return emailNode.get("email").asText();
            }
        }

        if (emailsNode.isArray() && !emailsNode.isEmpty()) {
            return emailsNode.get(0).get("email").asText();
        }

        return null;

    }

    private Account getGoogleUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);

        ResponseEntity<String> userInfoResponse = restTemplate.exchange(
                googleUserInfoUrl,
                HttpMethod.GET,
                userInfoRequest,
                String.class);

        if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ErrorCode.OAUTH_LOAD_USER_INFO_ERROR);
        }

        String userInfo = userInfoResponse.getBody();
        JsonNode userInfoJson = mapper.readTree(userInfo);

        return Account.builder()
                .name(userInfoJson.get("name").asText())
                .email(userInfoJson.get("email").asText())
                .image(userInfoJson.get("picture").asText())
                .isVerified(userInfoJson.get("verified_email").asBoolean())
                .build();
    }

    private String extractGithubAccessToken(String response) {
        Map<String, String> queryPairs = new HashMap<>();
        assert response != null;
        String[] pairs = response.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return queryPairs.get("access_token");
    }

    private String extractGoogleAccessToken(String response) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(response);
        return jsonNode.get("access_token").asText();
    }

    private ResponseEntity<String> getOAuthAccessToken(String accessTokenEndpoint, String clientId, String clientSecret, String callbackUri, String code) throws JsonProcessingException {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("code", code);
        requestBody.add("redirect_uri", callbackUri);
        requestBody.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                accessTokenEndpoint,
                HttpMethod.POST,
                requestEntity,
                String.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        if (statusCode == HttpStatus.BAD_REQUEST) {
            throw new ApiException(ErrorCode.INVALID_OAUTH_CODE);
        } else if (statusCode != HttpStatus.OK) {
            throw new ApiException(ErrorCode.OAUTH_ACCESS_TOKEN_ERROR);
        }
        return responseEntity;

    }

    private String getOAuthUrl(String providerBaseUrl, String id, String scope, String callbackUri) {
        String responseType = "code";
        String encodedScope;

        encodedScope = URLEncoder.encode(scope, StandardCharsets.UTF_8);

        return providerBaseUrl + "?client_id=" + id +
                "&redirect_uri=" + callbackUri +
                "&response_type=" + responseType +
                "&scope=" + encodedScope;
    }

}
