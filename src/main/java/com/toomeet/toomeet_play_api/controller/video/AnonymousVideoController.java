package com.toomeet.toomeet_play_api.controller.video;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
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


    @GetMapping("/feeds")
    public ResponseEntity<ApiResponse<?>> getNewsfeeds(
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "l", required = false, defaultValue = "20") int limit
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.getNewsfeeds(page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/category")
//    public ResponseEntity<ApiResponse<?>> getAllCategory() {
//        ApiResponse<?> response = ApiResponse.success(videoService.getAllCategory());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/{videoId}/watch")
    public ResponseEntity<ApiResponse<?>> getVideoDetails(
            @PathVariable("videoId") String videoId
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.getVideoDetails(videoId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
