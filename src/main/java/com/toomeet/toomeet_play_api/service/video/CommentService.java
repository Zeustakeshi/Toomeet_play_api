package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.request.video.comment.NewCommentRequest;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentReactionResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ReactionType;

public interface CommentService {
    CommentResponse createComment(NewCommentRequest request, String videoId, Account account);

    CommentReactionResponse reactionComment(ReactionType reactionType, String commentId, String videoId, Account account);

    CommentReactionResponse unReactionComment(ReactionType reactionType, String commentId, String videoId, Account account);


}
