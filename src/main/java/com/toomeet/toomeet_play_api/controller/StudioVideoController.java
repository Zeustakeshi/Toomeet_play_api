package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.AddVideoToPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.dto.response.StudioVideoResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/studio/video")
@RequiredArgsConstructor
public class StudioVideoController {
    private final VideoService videoService;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getAllVideoInPlaylist(
            @RequestParam(value = "playlist", required = false) String playlistId,
            @AuthenticationPrincipal Account account
    ) {

        Set<StudioVideoResponse> videos;

        if (playlistId != null) {
            videos = videoService.getAllVideoFormPlaylist(playlistId);
        } else {
            videos = videoService.getAllVideoByOwner(account.getUserId());
        }
        ApiResponse<?> response = ApiResponse.success(videos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
