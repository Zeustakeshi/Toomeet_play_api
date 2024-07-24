package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.request.video.comment.NewCommentRequest;
import com.toomeet.toomeet_play_api.dto.request.video.comment.UpdateCommentRequest;
import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentReactionCountResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentResponse;
import com.toomeet.toomeet_play_api.dto.video.comment.CommentDetailDto;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.entity.video.Comment;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.PageMapper;
import com.toomeet.toomeet_play_api.mapper.VideoCommentMapper;
import com.toomeet.toomeet_play_api.repository.UserRepository;
import com.toomeet.toomeet_play_api.repository.video.CommentRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.video.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final VideoCommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PageMapper pageMapper;

    @Override
    public CommentResponse createComment(NewCommentRequest request, String videoId, Account account) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        checkVideoPermission(video.getId(), account);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(account.getUser())
                .video(video)
                .build();

        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PARENT_COMMENT_NOT_FOUND));
            if (parent.isReply()) comment.setParent(parent.getParent());
            else comment.setParent(parent);
        }

        Comment newComment = commentRepository.save(comment);

        return commentMapper.toCommentResponse(newComment);
    }

    @Override
    public String updateComment(UpdateCommentRequest request, String commentId, String videoId, Account account) {

        checkVideoPermission(videoId, account);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        checkCommentOwner(commentId, account.getUserId());

        comment.setContent(request.getContent());

        commentRepository.save(comment);

        return "comment has been updated";
    }

    @Override
    @Transactional
    public String deleteComment(String videoId, String commentId, Account account) {
        checkVideoPermission(videoId, account);

        commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        checkCommentOwner(commentId, account.getUserId());

        commentRepository.deleteComment(commentId);

        return "Your comment has been deleted!";
    }

    @Override
    @Transactional
    public PageableResponse<CommentResponse> getAllCommentByVideoId(String videoId, int page, int limit, Account account) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        checkVideoPermission(video.getId(), account);

        if (!video.isAllowedComment()) {
            throw new ApiException(ErrorCode.VIDEO_COMMENT_UNAVAILABLE);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Page<CommentDetailDto> comments = commentRepository.getAllByVideoIdAndParentId(
                videoId,
                null,
                account.getUserId(),
                PageRequest.of(page, limit, sort)
        );

        return pageMapper.toPageableResponse(
                comments.map(commentMapper::toCommentResponse)
        );
    }

    @Override
    public PageableResponse<CommentResponse> getAllCommentCommentReplies(String videoId, String parentId, int page, int limit, Account account) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        checkVideoPermission(video.getId(), account);

        if (!video.isAllowedComment()) {
            throw new ApiException(ErrorCode.VIDEO_COMMENT_UNAVAILABLE);
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        Page<CommentDetailDto> comments = commentRepository.getAllByVideoIdAndParentId(
                videoId,
                parentId,
                account.getUserId(),
                PageRequest.of(page, limit, sort)
        );

        return pageMapper.toPageableResponse(
                comments.map(commentMapper::toCommentResponse)
        );
    }

    @Override
    @Transactional
    public CommentReactionCountResponse reactionComment(ReactionType reactionType, String commentId, String videoId, Account account) {

        checkVideoPermission(videoId, account);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (reactionType == ReactionType.LIKE) return likeComment(comment, user);
        else return dislikeComment(comment, user);
    }

    @Override
    @Transactional
    public CommentReactionCountResponse unReactionComment(ReactionType reactionType, String commentId, String videoId, Account account) {

        checkVideoPermission(videoId, account);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (reactionType == ReactionType.LIKE) return unLikeComment(comment, user);
        else return unDislikeComment(comment, user);
    }

    private CommentReactionCountResponse likeComment(Comment comment, User user) {
        // TODO: fix lazy load user (don't query user watched list in this case)
        if (commentRepository.isUserLikedComment(user.getId(), comment.getId())) {
            throw new ApiException(ErrorCode.USER_ALREADY_LIKED_COMMENT);
        }
        comment.getLikes().add(user);
        comment.getDislikes().remove(user);
        commentRepository.save(comment);
        return commentMapper.toCommentReactionResponse(comment);
    }

    private CommentReactionCountResponse unLikeComment(Comment comment, User user) {
        if (!comment.getLikes().contains(user)) {
            throw new ApiException(ErrorCode.COMMENT_NOT_LIKED_YET);
        }
        comment.getLikes().remove(user);
        commentRepository.save(comment);
        return commentMapper.toCommentReactionResponse(comment);
    }

    private CommentReactionCountResponse dislikeComment(Comment comment, User user) {
        if (comment.getDislikes().contains(user)) {
            throw new ApiException(ErrorCode.USER_ALREADY_DISLIKED_COMMENT);
        }
        comment.getDislikes().add(user);
        comment.getLikes().remove(user);
        commentRepository.save(comment);
        return commentMapper.toCommentReactionResponse(comment);
    }

    private CommentReactionCountResponse unDislikeComment(Comment comment, User user) {
        if (!comment.getDislikes().contains(user)) {
            throw new ApiException(ErrorCode.COMMENT_NOT_DISLIKED_YET);
        }
        comment.getDislikes().remove(user);
        commentRepository.save(comment);
        return commentMapper.toCommentReactionResponse(comment);
    }

    private void checkCommentOwner(String commentId, String userId) {
        if (!commentRepository.isCommentOwner(commentId, userId)) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void checkVideoPermission(String videoId, Account account) {
        if (!videoRepository.canCommentVideo(videoId)) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }
        // check something here
    }


}
