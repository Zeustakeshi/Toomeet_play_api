package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/my-info")
    public ResponseEntity<ApiResponse<?>> getUserAuthenticationInfo(
            @AuthenticationPrincipal User user,
            HttpServletRequest request
    ) {
        ApiResponse<?> response = ApiResponse.success(request, userService.getUserAuthentication(user));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
