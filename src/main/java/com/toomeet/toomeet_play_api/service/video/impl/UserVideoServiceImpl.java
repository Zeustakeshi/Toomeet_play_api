/*
 *  UserVideoServiceImpl
 *  @author: Minhhieuano
 *  @created 7/31/2024 9:21 PM
 * */

package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.UserVideoDetailResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoNewsfeedResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoReactionResponse;
import com.toomeet.toomeet_play_api.dto.video.VideoNewsfeedDto;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import com.toomeet.toomeet_play_api.enums.Visibility;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.PageMapper;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.user.UserRepository;
import com.toomeet.toomeet_play_api.repository.video.UserVideoRepository;
import com.toomeet.toomeet_play_api.service.video.UserVideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserVideoServiceImpl implements UserVideoService {

    private final UserVideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;
    private final PageMapper pageMapper;

    @Override
    public PageableResponse<VideoNewsfeedResponse> getNewsfeeds(int page, int limit, Account account) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Page<VideoNewsfeedDto> videos = videoRepository.getNewsfeeds(PageRequest.of(page, limit, sort));
        // TODO:  get newsfeeds of specify user
        Page<VideoNewsfeedResponse> videoResponses = videos.map(videoMapper::toVideoPreviewResponse);
        return pageMapper.toPageableResponse(videoResponses);
    }

    @Override
    public UserVideoDetailResponse getVideoDetails(String videoId, Account account) {
        return videoMapper.toUserVideoDetailResponse(videoRepository.getVideoDetail(videoId, account.getUserId()));
    }

    @Override
    @Transactional
    public VideoReactionResponse reactionVideo(String videoId, ReactionType type, Account account) {

        Video video = videoRepository.findById(videoId).orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        if (video.getVisibility() != Visibility.PUBLIC) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }

        User user = userRepository
                .findById(account.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (type == ReactionType.LIKE) return likeVideo(video, user);
        else return dislikeVideo(video, user);
    }

    @Override
    @Transactional
    public VideoReactionResponse unReactionVideo(String videoId, ReactionType type, Account account) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        User user = userRepository
                .findById(account.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (type == ReactionType.LIKE) return unLikeVideo(video, user);
        else return unDislikeVideo(video, user);
    }

    //    @Override
    //    public VideoInteractionResponse getVideoInteraction(String videoId, Account account) {
    //        return videoRepository.getVideoInteraction(videoId, account.getUserId());
    //    }

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
