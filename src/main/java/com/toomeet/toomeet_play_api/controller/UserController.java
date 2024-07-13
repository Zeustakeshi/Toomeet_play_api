package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.user.AddVideoHistoryRequest;
import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.user.AccountService;
import com.toomeet.toomeet_play_api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/my-info")
    public ResponseEntity<ApiResponse<?>> getUserAuthenticationInfo(
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(accountService.getAccountInfo(account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/history")
    public ResponseEntity<ApiResponse<?>> addHistory(
            @RequestBody @Valid AddVideoHistoryRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(userService.addVideoHistory(request, account));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
