package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TestController {
    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal Account user) {
        return "Xin chào, " + user.getEmail();
    }
}
