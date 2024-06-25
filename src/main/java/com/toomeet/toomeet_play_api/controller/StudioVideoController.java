package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.AddVideoToPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/studio/video")
@RequiredArgsConstructor
public class StudioVideoController {
    private final VideoService videoService;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> uploadVideo(
            @RequestParam(value = "file") MultipartFile file,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.createVideo(file, account.getUserId()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("add-playlist")
    public ResponseEntity<ApiResponse<?>> addPlaylist(
            @RequestBody @Valid AddVideoToPlaylistRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.addToPlaylist(request.getVideoId(), request.getPlaylistId(), account.getUserId())
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
