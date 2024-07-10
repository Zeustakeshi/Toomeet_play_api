package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoPreviewResponse;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.mapper.ChannelMapper;
import com.toomeet.toomeet_play_api.mapper.VideoCategoryMapper;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.video.CategoryRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.video.AnonymousVideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AnonymousVideoServiceImpl implements AnonymousVideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final ChannelMapper channelMapper;
    private final CategoryRepository categoryRepository;
    private final VideoCategoryMapper videoCategoryMapper;

    @Override
    @Transactional
    public List<VideoPreviewResponse> getAllVideo() {
        List<Video> videos = videoRepository.getAllAnonymous();

        return videos.stream().map(video -> {
            VideoPreviewResponse videoResponse = videoMapper.toVideoPreviewResponse(video);

            ChannelGeneralResponse channelResponse = channelMapper.toChannelGeneralResponse(video.getChannel());

            videoResponse.setViewCount(videoRepository.countVideoView(video.getId()));
            videoResponse.setChannel(channelResponse);

            return videoResponse;
        }).collect(Collectors.toList());

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
