package com.toomeet.toomeet_play_api.controller.studio;

import com.toomeet.toomeet_play_api.dto.request.UpdateVideoCategoryRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoMetadataRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoSettingRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoTagRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.video.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("studio/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;


    @GetMapping("{videoId}")
    public ResponseEntity<ApiResponse<?>> getVideoInfo(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.getVideoById(videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("upload")
    public ResponseEntity<ApiResponse<?>> uploadVideo(
            @RequestParam("video") MultipartFile video,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.uploadVideo(video, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("upload/thumbnail/{videoId}")
    public ResponseEntity<ApiResponse<?>> updateThumbnail(
            @PathVariable("videoId") String videoId,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.uploadThumbnail(thumbnail, videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{videoId}/metadata")
    public ResponseEntity<ApiResponse<?>> updateVideoMetadata(
            @PathVariable String videoId,
            @RequestBody @Valid UpdateVideoMetadataRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.updateVideoMetadata(request, videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{videoId}/settings")
    public ResponseEntity<ApiResponse<?>> updateVideoSettings(
            @PathVariable String videoId,
            @RequestBody @Valid UpdateVideoSettingRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.updateVideoSettings(request, videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{videoId}/tags")
    public ResponseEntity<ApiResponse<?>> updateVideoTag(
            @PathVariable("videoId") String videoId,
            @RequestBody UpdateVideoTagRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.updateVideoTag(request, videoId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("{videoId}/category")
    public ResponseEntity<ApiResponse<?>> updateVideoCategory(
            @PathVariable("videoId") String videoId,
            @RequestBody UpdateVideoCategoryRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.updateVideoCategory(request, videoId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
