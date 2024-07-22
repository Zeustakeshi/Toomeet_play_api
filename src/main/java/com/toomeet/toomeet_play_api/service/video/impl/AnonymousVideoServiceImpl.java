package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoDetailPublicResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoPreviewResponse;
import com.toomeet.toomeet_play_api.dto.video.VideoPreviewDto;
import com.toomeet.toomeet_play_api.mapper.PageMapper;
import com.toomeet.toomeet_play_api.mapper.VideoCategoryMapper;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.video.AnonymousVideoRepository;
import com.toomeet.toomeet_play_api.repository.video.CategoryRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.video.AnonymousVideoService;
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
    private final CategoryRepository categoryRepository;
    private final VideoCategoryMapper videoCategoryMapper;
    private final AnonymousVideoRepository anonymousVideoRepository;
    private final VideoRepository videoRepository;
    private final PageMapper pageMapper;

    @Override
    public PageableResponse<VideoPreviewResponse> getAllVideo(int page, int limit) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        Page<VideoPreviewDto> videos = anonymousVideoRepository.getAll(PageRequest.of(page, limit, sort));

        Page<VideoPreviewResponse> videoResponses = videos.map(videoMapper::toVideoPreviewResponse);
        return pageMapper.toPageableResponse(videoResponses);
    }

    @Override
    public VideoDetailPublicResponse getVideoDetails(String videoId) {
        return videoMapper.toVideoDetailPublicResponse(videoRepository.getVideoDetailPublic(videoId));
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
