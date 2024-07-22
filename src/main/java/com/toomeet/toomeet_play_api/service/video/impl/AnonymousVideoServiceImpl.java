package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoPreviewResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoWatchResponse;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.ChannelMapper;
import com.toomeet.toomeet_play_api.mapper.PageMapper;
import com.toomeet.toomeet_play_api.mapper.VideoCategoryMapper;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.video.AnonymousVideoRepository;
import com.toomeet.toomeet_play_api.repository.video.CategoryRepository;
import com.toomeet.toomeet_play_api.repository.video.TagRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.video.AnonymousVideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AnonymousVideoServiceImpl implements AnonymousVideoService {

    private final VideoMapper videoMapper;
    private final ChannelMapper channelMapper;
    private final CategoryRepository categoryRepository;
    private final VideoCategoryMapper videoCategoryMapper;
    private final AnonymousVideoRepository anonymousVideoRepository;
    private final VideoRepository videoRepository;
    private final PageMapper pageMapper;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public PageableResponse<VideoPreviewResponse> getAllVideo(int page, int limit) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        Page<Video> videos = anonymousVideoRepository.getAllAnonymous(PageRequest.of(page, limit, sort));

        Page<VideoPreviewResponse> videoResponses = videos.map(video -> {
            VideoPreviewResponse videoResponse = videoMapper.toVideoPreviewResponse(video);
            videoResponse.setViewCount(videoRepository.countVideoView(video.getId()));
            return videoResponse;
        });

        return pageMapper.toPageableResponse(videoResponses);

    }

    @Override
    @Transactional
    public VideoWatchResponse getVideoDetails(String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));
        VideoWatchResponse response = videoMapper.toVideoWatchResponse(video);
        response.setLikeCount(videoRepository.countVideoLike(videoId));
        response.setDislikeCount(videoRepository.countVideoDislike(videoId));
        response.setCommentCount(videoRepository.countVideoComment(videoId));
        return response;
    }

    @Override
    public List<VideoCategoryResponse> getAllCategory() {
        return categoryRepository
                .findAll()
                .stream()
                .map(videoCategoryMapper::toCategoryResponse)
                .toList();
    }
}
