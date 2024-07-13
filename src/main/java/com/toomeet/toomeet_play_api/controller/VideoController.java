package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import com.toomeet.toomeet_play_api.service.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("{videoId}/reaction")
    public ResponseEntity<ApiResponse<?>> likeVideo(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal Account account,
            @RequestParam("type") ReactionType type
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.reactionVideo(videoId, type, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("{videoId}/un-reaction")
    public ResponseEntity<ApiResponse<?>> unLikeVideo(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal Account account,
            @RequestParam("type") ReactionType type
    ) {
        ApiResponse<?> response = ApiResponse.success(
                videoService.unReactionVideo(videoId, type, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
