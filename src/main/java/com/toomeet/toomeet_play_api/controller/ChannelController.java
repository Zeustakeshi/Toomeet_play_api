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
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/studio/channel")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @GetMapping("general")
    public ResponseEntity<ApiResponse<?>> getChannelGeneralInfo(
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.getChanelGeneralInfo(account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createChannel(
            @RequestBody @Valid CreateChannelRequest createChannelRequest,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.createChannel(createChannelRequest, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/upload-avatar")
    public ResponseEntity<ApiResponse<?>> uploadAvatar(
            @RequestParam(value = "image") MultipartFile image,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(channelService.uploadChannelAvatar(image, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
