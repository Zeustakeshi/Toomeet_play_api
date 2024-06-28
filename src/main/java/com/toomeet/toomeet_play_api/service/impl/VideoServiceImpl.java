package com.toomeet.toomeet_play_api.service.impl;

import com.cloudinary.Cloudinary;
import com.toomeet.toomeet_play_api.dto.response.StudioVideoResponse;
import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.entity.Playlist;
import com.toomeet.toomeet_play_api.entity.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.VideoStatus;
import com.toomeet.toomeet_play_api.event.UploadVideoEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.VideoRepository;
import com.toomeet.toomeet_play_api.service.ChannelService;
import com.toomeet.toomeet_play_api.service.PlaylistService;
import com.toomeet.toomeet_play_api.service.VideoService;
import com.toomeet.toomeet_play_api.utils.ResourceUploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final Cloudinary cloudinary;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final ApplicationEventPublisher publisher;
    private final PlaylistService playlistService;
    private final ChannelService channelService;
    private final ResourceUploader resourceUploader;

    @Async
    @Override
    public void uploadVideo(String videoId, String userId, byte[] file) {

        Video video = Optional.ofNullable(videoRepository.findByVideoId(videoId))
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));
        try {
            ResourceUploaderResponse uploadResponse = resourceUploader.uploadVideo(file, video.getVideoId(), "/video");
            video.setUrl(uploadResponse.getUrl());
            video.setWidth(uploadResponse.getWidth());
            video.setHeight(uploadResponse.getHeight());
            video.setFormat(uploadResponse.getFormat());
            video.setStatus(VideoStatus.ENABLE);

            //TODO: send notify video upload success to user (userId)
        } catch (IOException | RuntimeException e) {
            video.setStatus(VideoStatus.FAILED);
            //TODO: send notify video upload failed to user (userId)
        } finally {
            videoRepository.save(video);
        }
    }

    @Override
    public StudioVideoResponse createVideo(MultipartFile file, String userId) {

        Channel channel = Optional.ofNullable(channelService.getChannelByOwnerId(userId))
                .orElseThrow(() -> new ApiException(ErrorCode.CHANNEL_NOT_FOUND));


        Video video = Video.builder()
                .title("Untitled Videos-" + UUID.randomUUID())
                .channel(channel)
                .description("No description")
                .build();

        Video newVideo = videoRepository.save(video);

        try {
            UploadVideoEvent event = UploadVideoEvent.builder()
                    .video(file.getInputStream().readAllBytes())
                    .userId(userId)
                    .videoId(newVideo.getVideoId())
                    .build();

            publisher.publishEvent(event);

            return videoMapper.toVideoResponse(newVideo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Video getVideoByVideoId(String videoId) {
        return videoRepository.findByVideoId(videoId);
    }

    @Override
    public Set<StudioVideoResponse> getAllVideoFormPlaylist(String playlistId) {
        return videoRepository.findAllByPlaylistId(playlistId)
                .stream().map(videoMapper::toVideoResponse).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public String addToPlaylist(String videoId, String playlistId, String userId) {

        Video video = Optional.ofNullable(getVideoByVideoId(videoId))
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        Playlist playlist = Optional.ofNullable(playlistService.getByPlaylistId(playlistId))
                .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));


        if (!playlistService.isPlaylistOwner(playlistId, userId)) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }


        video.setPlaylist(playlist);
        videoRepository.save(video);
        return "Added video " + video.getTitle() + "to playlist: " + playlist.getName();
    }

    @Override
    public Set<StudioVideoResponse> getAllVideoByOwner(String userId) {
        return videoRepository.findAllByOwnerId(userId)
                .stream().map(videoMapper::toVideoResponse).collect(Collectors.toSet());
    }
}
