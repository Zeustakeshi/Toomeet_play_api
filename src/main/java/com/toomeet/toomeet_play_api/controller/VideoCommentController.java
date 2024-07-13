package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.video.comment.NewCommentRequest;
import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import com.toomeet.toomeet_play_api.service.video.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video/{videoId}/comments")
@RequiredArgsConstructor
public class VideoCommentController {
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createComment(
            @PathVariable("videoId") String videoId,
            @RequestBody @Valid NewCommentRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(commentService.createComment(request, videoId, account));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("{commentId}/reaction")
    public ResponseEntity<ApiResponse<?>> reactionComment(
            @PathVariable("videoId") String videoId,
            @PathVariable("commentId") String commentId,
            @AuthenticationPrincipal Account account,
            @RequestParam("type") ReactionType type
    ) {
        ApiResponse<?> response = ApiResponse.success(
                commentService.reactionComment(type, commentId, videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("{commentId}/un-reaction")
    public ResponseEntity<ApiResponse<?>> unReactionComment(
            @PathVariable("videoId") String videoId,
            @PathVariable("commentId") String commentId,
            @AuthenticationPrincipal Account account,
            @RequestParam("type") ReactionType type
    ) {
        ApiResponse<?> response = ApiResponse.success(
                commentService.unReactionComment(type, commentId, videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
