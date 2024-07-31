package com.toomeet.toomeet_play_api.controller.video;

import com.toomeet.toomeet_play_api.dto.request.video.*;
import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.video.StudioVideoService;
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
public class StudioVideoController {
    private final StudioVideoService studioVideoService;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getAllVideo(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(studioVideoService.getAllVideo(page, limit, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("{videoId}")
    public ResponseEntity<ApiResponse<?>> getVideoInfo(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                studioVideoService.getVideoById(videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{videoId}/tags")
    public ResponseEntity<ApiResponse<?>> getVideoTags(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                studioVideoService.getVideoTags(videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<?>> getTopVideo(
            @RequestParam(value = "n", required = false, defaultValue = "3") int count,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(studioVideoService.getTopVideo(count, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("upload")
    public ResponseEntity<ApiResponse<?>> uploadVideo(
            @RequestParam("video") MultipartFile video,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                studioVideoService.uploadVideo(video, account)
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
                studioVideoService.uploadThumbnail(thumbnail, videoId, account)
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
                studioVideoService.updateVideoMetadata(request, videoId, account)
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
                studioVideoService.updateVideoSettings(request, videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{videoId}/details")
    public ResponseEntity<ApiResponse<?>> updateVideoDetails(
            @PathVariable("videoId") String videoId,
            @RequestBody UpdateVideoDetails request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(studioVideoService.updateVideoDetails(request, videoId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{videoId}/tags")
    public ResponseEntity<ApiResponse<?>> updateVideoTag(
            @PathVariable("videoId") String videoId,
            @RequestBody UpdateVideoTagRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(studioVideoService.updateVideoTag(request, videoId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{videoId}/category")
    public ResponseEntity<ApiResponse<?>> updateVideoCategory(
            @PathVariable("videoId") String videoId,
            @RequestBody UpdateVideoCategoryRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(studioVideoService.updateVideoCategory(request, videoId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @DeleteMapping("{videoId}")
//    public ResponseEntity<ApiResponse<?>> deleteVideo(
//            @PathVariable("videoId") String videoId,
//            @AuthenticationPrincipal Account account
//    ) {
//        ApiResponse<?> response = ApiResponse.success(studioVideoService.deleteVideo(videoId, account));
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

}
