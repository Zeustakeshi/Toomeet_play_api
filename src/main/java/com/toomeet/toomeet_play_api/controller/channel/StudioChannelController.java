package com.toomeet.toomeet_play_api.controller.channel;

import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelDescriptionRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelNameRequest;
import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.channel.StudioChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/studio/channel")
@RequiredArgsConstructor
public class StudioChannelController {
    private final StudioChannelService channelService;

    @GetMapping("/general")
    public ResponseEntity<ApiResponse<?>> getChannelGeneral(
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(channelService.getChannelGeneral(account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<?>> getChannelAnalytics(
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(channelService.getChannelAnalytics(account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/name")
    public ResponseEntity<ApiResponse<?>> updateChannelName(
            @RequestBody @Valid UpdateChannelNameRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.updateChannelName(request, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/description")
    public ResponseEntity<ApiResponse<?>> updateChannelDescription(
            @RequestBody @Valid UpdateChannelDescriptionRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.updateChannelDescription(request, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/avatar")
    public ResponseEntity<ApiResponse<?>> updateAvatar(
            @RequestParam("avatar") MultipartFile avatar,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                channelService.updateChannelAvatar(avatar, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
