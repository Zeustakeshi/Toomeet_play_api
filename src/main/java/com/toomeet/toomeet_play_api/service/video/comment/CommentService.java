package com.toomeet.toomeet_play_api.service.video.comment;

import com.toomeet.toomeet_play_api.dto.request.video.comment.NewCommentRequest;
import com.toomeet.toomeet_play_api.dto.request.video.comment.UpdateCommentRequest;
import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentReactionCountResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ReactionType;

public interface CommentService {
    CommentResponse createComment(NewCommentRequest request, String videoId, Account account);

    CommentReactionCountResponse reactionComment(
            ReactionType reactionType, String commentId, String videoId, Account account);

    CommentReactionCountResponse unReactionComment(
            ReactionType reactionType, String commentId, String videoId, Account account);

    PageableResponse<CommentResponse> getAllCommentByVideoId(String videoId, int page, int limit, Account account);

    PageableResponse<CommentResponse> getAllCommentCommentReplies(
            String videoId, String parentId, int page, int limit, Account account);

    String deleteComment(String videoId, String commentId, Account account);

    String updateComment(UpdateCommentRequest request, String commentId, String videoId, Account account);
}
