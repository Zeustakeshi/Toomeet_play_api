package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.request.video.comment.NewCommentRequest;
import com.toomeet.toomeet_play_api.dto.request.video.comment.UpdateCommentRequest;
import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentReactionResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.entity.video.Comment;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import com.toomeet.toomeet_play_api.enums.Visibility;
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

import java.util.Objects;

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

        checkVideoPermission(video, account);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(account.getUser())
                .video(video)
                .build();

        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.PARENT_COMMENT_NOT_FOUND));
            comment.setParent(parent);
        }

        return commentMapper.toCommentResponse(commentRepository.save(comment), account.getUserId());
    }

    @Override
    @Transactional
    public CommentResponse updateComment(UpdateCommentRequest request, String commentId, String videoId, Account account) {

        checkVideoPermission(videoId, account);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        checkCommentOwner(comment, account);

        comment.setContent(request.getContent());

        commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment, account.getUserId());
    }

    @Override
    @Transactional
    public PageableResponse<CommentResponse> getAllCommentByVideoId(String videoId, int page, int limit, Account account) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        checkVideoPermission(video, account);

        if (!video.isAllowedComment()) {
            throw new ApiException(ErrorCode.VIDEO_COMMENT_UNAVAILABLE);
        }


        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Page<Comment> comments = commentRepository.getAllByVideoId(videoId, PageRequest.of(page, limit, sort));

        Page<CommentResponse> commentResponsePage = comments.map(comment -> {
            var commentResponse = commentMapper.toCommentResponse(comment, account.getUserId());

            commentResponse.setTotalDislikes(commentRepository.countCommentDislike(comment.getId()));
            commentResponse.setTotalLikes(commentRepository.countCommentLike(comment.getId()));
            commentResponse.setTotalReplies(commentRepository.countCommentReplies(comment.getId()));

            return commentResponse;
        });

        return pageMapper.toPageableResponse(commentResponsePage);
    }

    @Override
    @Transactional
    public CommentReactionResponse reactionComment(ReactionType reactionType, String commentId, String videoId, Account account) {

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
    public CommentReactionResponse unReactionComment(ReactionType reactionType, String commentId, String videoId, Account account) {

        checkVideoPermission(videoId, account);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (reactionType == ReactionType.LIKE) return unLikeComment(comment, user);
        else return unDislikeComment(comment, user);
    }

    @Override
    @Transactional
    public String deleteComment(String videoId, String commentId, Account account) {
        checkVideoPermission(videoId, account);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

        checkCommentOwner(comment, account);

        commentRepository.delete(comment);

        return "Your comment has been deleted!";
    }

    private CommentReactionResponse likeComment(Comment comment, User user) {
        if (comment.getLikes().contains(user)) {
            throw new ApiException(ErrorCode.USER_ALREADY_LIKED_COMMENT);
        }
        comment.getLikes().add(user);
        comment.getDislikes().remove(user);

        commentRepository.save(comment);

        return commentMapper.toCommentReactionResponse(comment);
    }

    private CommentReactionResponse unLikeComment(Comment comment, User user) {
        if (!comment.getLikes().contains(user)) {
            throw new ApiException(ErrorCode.COMMENT_NOT_LIKED_YET);
        }
        comment.getLikes().remove(user);
        commentRepository.save(comment);
        return commentMapper.toCommentReactionResponse(comment);
    }

    private CommentReactionResponse dislikeComment(Comment comment, User user) {
        if (comment.getDislikes().contains(user)) {
            throw new ApiException(ErrorCode.USER_ALREADY_DISLIKED_COMMENT);
        }
        comment.getDislikes().add(user);
        comment.getLikes().remove(user);

        commentRepository.save(comment);

        return commentMapper.toCommentReactionResponse(comment);
    }

    private CommentReactionResponse unDislikeComment(Comment comment, User user) {
        if (!comment.getDislikes().contains(user)) {
            throw new ApiException(ErrorCode.COMMENT_NOT_DISLIKED_YET);
        }
        comment.getDislikes().remove(user);
        commentRepository.save(comment);
        return commentMapper.toCommentReactionResponse(comment);
    }

    private void checkCommentOwner(Comment comment, Account account) {
        if (!Objects.equals(comment.getUser().getId(), account.getUserId())) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }


    }

    private void checkVideoPermission(String videoId, Account account) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        checkVideoPermission(video, account);
    }

    private void checkVideoPermission(Video video, Account account) {
        if (video.getVisibility() != Visibility.PUBLIC) throw new ApiException(ErrorCode.ACCESS_DENIED);
    }
}
