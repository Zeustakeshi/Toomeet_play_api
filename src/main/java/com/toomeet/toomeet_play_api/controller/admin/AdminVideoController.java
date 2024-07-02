package com.toomeet.toomeet_play_api.controller.admin;

import com.toomeet.toomeet_play_api.dto.request.CreateVideoCategoryRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.service.video.AdminVideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/video")
@RequiredArgsConstructor
public class AdminVideoController {
    private final AdminVideoService videoService;

    @PostMapping("/category")
    public ResponseEntity<ApiResponse<?>> createCategory(
            @RequestBody @Valid CreateVideoCategoryRequest request
    ) {
        ApiResponse<?> response = ApiResponse.success(videoService.createCategory(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
