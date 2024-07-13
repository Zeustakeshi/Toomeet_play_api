package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.video.comment.NewCommentRequest;
import com.toomeet.toomeet_play_api.dto.request.video.comment.UpdateCommentRequest;
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

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllComment(
            @PathVariable("videoId") String videoId,
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "l", required = false, defaultValue = "5") int limit,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                commentService.getAllCommentByVideoId(videoId, page, limit, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("{commentId}")
    public ResponseEntity<ApiResponse<?>> updateComment(
            @PathVariable("videoId") String videoId,
            @PathVariable("commentId") String commentId,
            @AuthenticationPrincipal Account account,
            @RequestBody @Valid UpdateCommentRequest request
    ) {
        ApiResponse<?> response = ApiResponse.success(
                commentService.updateComment(request, commentId, videoId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<ApiResponse<?>> deleteComment(
            @PathVariable("videoId") String videoId,
            @PathVariable("commentId") String commentId,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                commentService.deleteComment(videoId, commentId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
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
