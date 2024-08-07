package com.toomeet.toomeet_play_api.controller.channel;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.channel.UserChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class UserChannelController {

    private final UserChannelService channelService;

    @GetMapping("{channelId}/basic")
    public ResponseEntity<ApiResponse<?>> getChannel(
            @PathVariable("channelId") String channelId, @AuthenticationPrincipal Account account) {
        ApiResponse<?> response = ApiResponse.success(channelService.getBasicInfo(channelId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("{channelId}/subscribe")
    public ResponseEntity<ApiResponse<?>> subscribeChannel(
            @PathVariable("channelId") String channelId, @AuthenticationPrincipal Account account) {
        ApiResponse<?> response = ApiResponse.success(channelService.subscribe(channelId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("{channelId}/un-subscribe")
    public ResponseEntity<ApiResponse<?>> unSubscribeChannel(
            @PathVariable("channelId") String channelId, @AuthenticationPrincipal Account account) {
        ApiResponse<?> response = ApiResponse.success(channelService.unsubscribe(channelId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
