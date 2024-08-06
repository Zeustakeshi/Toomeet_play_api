package com.toomeet.toomeet_play_api.controller.video;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import com.toomeet.toomeet_play_api.service.video.UserVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class UserVideoController {

    private final UserVideoService videoService;

    @GetMapping("feeds")
    public ResponseEntity<ApiResponse<?>> getNewsfeeds(
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "l", required = false, defaultValue = "20") int limit,
            @AuthenticationPrincipal Account account) {
        ApiResponse<?> response = ApiResponse.success(videoService.getNewsfeeds(page, limit, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{videoId}/watch")
    public ResponseEntity<ApiResponse<?>> getVideoDetails(
            @PathVariable("videoId") String videoId, @AuthenticationPrincipal Account account) {
        ApiResponse<?> response = ApiResponse.success(videoService.getVideoDetails(videoId, account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("{videoId}/reaction")
    public ResponseEntity<ApiResponse<?>> likeVideo(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal Account account,
            @RequestParam("type") ReactionType type) {
        ApiResponse<?> response = ApiResponse.success(videoService.reactionVideo(videoId, type, account));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("{videoId}/un-reaction")
    public ResponseEntity<ApiResponse<?>> unLikeVideo(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal Account account,
            @RequestParam("type") ReactionType type) {
        ApiResponse<?> response = ApiResponse.success(videoService.unReactionVideo(videoId, type, account));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //    @GetMapping("/{videoId}/interaction")
    //    public ResponseEntity<ApiResponse<?>> getUserInteraction(
    //            @PathVariable("videoId") String videoId,
    //            @AuthenticationPrincipal Account account
    //    ) {
    //        ApiResponse<?> response = ApiResponse.success(videoService.getVideoInteraction(videoId, account));
    //        return new ResponseEntity<>(response, HttpStatus.OK);
    //    }

}
