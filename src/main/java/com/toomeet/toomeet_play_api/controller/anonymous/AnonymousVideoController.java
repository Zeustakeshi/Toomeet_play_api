package com.toomeet.toomeet_play_api.controller.anonymous;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.video.AnonymousVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("anonymous/video")
@RequiredArgsConstructor
public class AnonymousVideoController {
    private final AnonymousVideoService videoService;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getAllVideo(
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "l", required = false, defaultValue = "20") int limit
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.getAllVideo(page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse<?>> getAllCategory() {
        ApiResponse<?> response = ApiResponse.success(videoService.getAllCategory());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{videoId}/watch")
    public ResponseEntity<ApiResponse<?>> getVideo(
            @PathVariable("videoId") String videoId
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.getVideoDetails(videoId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
