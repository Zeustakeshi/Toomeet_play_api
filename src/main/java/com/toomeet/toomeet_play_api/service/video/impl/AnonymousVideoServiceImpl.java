package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.AnonymousVideoDetailResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoNewsfeedResponse;
import com.toomeet.toomeet_play_api.dto.video.VideoNewsfeedDto;
import com.toomeet.toomeet_play_api.mapper.PageMapper;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.video.AnonymousVideoRepository;
import com.toomeet.toomeet_play_api.service.video.AnonymousVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnonymousVideoServiceImpl implements AnonymousVideoService {

    private final VideoMapper videoMapper;
    private final AnonymousVideoRepository videoRepository;
    private final PageMapper pageMapper;

    @Override
    public PageableResponse<VideoNewsfeedResponse> getNewsfeeds(int page, int limit) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        Page<VideoNewsfeedDto> videos = videoRepository.getNewsfeeds(PageRequest.of(page, limit, sort));

        Page<VideoNewsfeedResponse> videoResponses = videos.map(videoMapper::toVideoPreviewResponse);
        return pageMapper.toPageableResponse(videoResponses);
    }

    @Override
    public AnonymousVideoDetailResponse getVideoDetails(String videoId) {
        return videoMapper.toAnonymousVideoDetailResponse(videoRepository.getVideoDetail(videoId));
    }
}
