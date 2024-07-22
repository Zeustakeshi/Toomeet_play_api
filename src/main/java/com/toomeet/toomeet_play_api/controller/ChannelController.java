package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    // Subscribe channel
    @PostMapping("{channelId}/subscribe")
    public ResponseEntity<ApiResponse<?>> subscribeChannel(
            @PathVariable("channelId") String channelId,
            @AuthenticationPrincipal Account account
    ) {
        // TODO: handle subscribe channel
        ApiResponse<?> response = ApiResponse.success(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // UnSubscribe channel
    @PostMapping("{channelId}/un-subscribe")
    public ResponseEntity<ApiResponse<?>> unSubscribeChannel(
            @PathVariable("channelId") String channelId,
            @AuthenticationPrincipal Account account
    ) {
        // TODO: handle unsubscribe channel
        ApiResponse<?> response = ApiResponse.success(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
