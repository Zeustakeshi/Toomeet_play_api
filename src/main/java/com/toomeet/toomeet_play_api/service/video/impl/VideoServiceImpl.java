package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.response.video.VideoDetailPublicResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoInteractionResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoReactionResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import com.toomeet.toomeet_play_api.enums.Visibility;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.repository.UserRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.video.VideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;


    @Override
    public VideoDetailPublicResponse getVideoDetail(String videoId) {
        // get video and channel
        return null;
    }

    @Override
    @Transactional
    public VideoReactionResponse reactionVideo(String videoId, ReactionType type, Account account) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        if (video.getVisibility() != Visibility.PUBLIC) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }

        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));


        if (type == ReactionType.LIKE) return likeVideo(video, user);
        else return dislikeVideo(video, user);
    }

    @Override
    @Transactional
    public VideoReactionResponse unReactionVideo(String videoId, ReactionType type, Account account) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (type == ReactionType.LIKE) return unLikeVideo(video, user);
        else return unDislikeVideo(video, user);
    }

    @Override
    public VideoInteractionResponse getVideoInteraction(String videoId, Account account) {
        return videoRepository.getVideoInteraction(videoId, account.getUserId());
    }

    private VideoReactionResponse likeVideo(Video video, User user) {
        // TODO: fix lazy load user (don't query user watched list in this case)
        if (video.getLikes().contains(user)) {
            throw new ApiException(ErrorCode.USER_ALREADY_LIKED_VIDEO);
        }
        video.getLikes().add(user);
        video.getDislikes().remove(user);
        videoRepository.save(video);
        return getVideoReactionCount(video);
    }

    private VideoReactionResponse dislikeVideo(Video video, User user) {
        if (video.getDislikes().contains(user)) {
            throw new ApiException(ErrorCode.USER_ALREADY_DISLIKED_VIDEO);
        }
        video.getDislikes().add(user);
        video.getLikes().remove(user);
        videoRepository.save(video);
        return getVideoReactionCount(video);
    }

    private VideoReactionResponse unLikeVideo(Video video, User user) {
        if (!video.getLikes().contains(user)) {
            throw new ApiException(ErrorCode.VIDEO_NOT_LIKED_YET);
        }
        video.getLikes().remove(user);
        videoRepository.save(video);
        return getVideoReactionCount(video);
    }

    private VideoReactionResponse unDislikeVideo(Video video, User user) {
        if (!video.getDislikes().contains(user)) {
            throw new ApiException(ErrorCode.VIDEO_NOT_DISLIKED_YET);
        }
        video.getDislikes().remove(user);
        videoRepository.save(video);
        return getVideoReactionCount(video);
    }

    private VideoReactionResponse getVideoReactionCount(Video video) {
        return VideoReactionResponse.builder()
                .like(video.getLikes().size())
                .dislike(video.getDislikes().size())
                .build();
    }

}
