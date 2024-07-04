package com.toomeet.toomeet_play_api.controller.anonymous;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.service.video.AnonymousVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("anonymous/video")
@RequiredArgsConstructor
public class AnonymousVideoController {
    private final AnonymousVideoService videoService;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getAllVideo() {
        ApiResponse<?> response = ApiResponse.success(videoService.getAllVideo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse<?>> getAllCategory() {
        ApiResponse<?> response = ApiResponse.success(videoService.getAllCategory());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
