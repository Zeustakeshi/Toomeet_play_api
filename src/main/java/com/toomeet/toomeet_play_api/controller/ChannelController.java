package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.entity.User;
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
            @AuthenticationPrincipal User user
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.getChanelGeneralInfo(channelId, user)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createChannel(
            @RequestBody @Valid CreateChannelRequest createChannelRequest,
            @AuthenticationPrincipal User user
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.createChannel(createChannelRequest, user.getUserId())
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
