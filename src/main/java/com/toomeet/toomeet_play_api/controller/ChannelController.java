package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/studio/channel")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @GetMapping("{channelId}/general")
    public ResponseEntity<ApiResponse<?>> getChannelGeneralInfo(
            @PathVariable String channelId,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.getChanelGeneralInfo(channelId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createChannel(
            @RequestBody @Valid CreateChannelRequest createChannelRequest,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.createChannel(createChannelRequest, account.getUserId())
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
