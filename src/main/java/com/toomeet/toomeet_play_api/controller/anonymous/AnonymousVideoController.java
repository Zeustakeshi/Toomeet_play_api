package com.toomeet.toomeet_play_api.controller.anonymous;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.service.util.ResourceService;
import com.toomeet.toomeet_play_api.service.video.AnonymousVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController()
@RequestMapping("anonymous/video")
@RequiredArgsConstructor
public class AnonymousVideoController {
    private final AnonymousVideoService videoService;
    private final ResourceService resourceService;

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

    @PostMapping("/test")
    public String test(
//            @RequestParam("file") MultipartFile file
    ) throws IOException {
//        ResourceUploaderResponse response = resourceService.uploadImage(
//                file.getInputStream().readAllBytes(),
//                "hello-world",
//                "/test"
//        );
//        return response.getUrl();
        return resourceService.generateSignedUrl("hello-world");
    }

    @PostMapping("/test/test")
    public String hello(@RequestBody Object body) {
        System.out.println("body = " + body);
        return "OK";
    }


}
