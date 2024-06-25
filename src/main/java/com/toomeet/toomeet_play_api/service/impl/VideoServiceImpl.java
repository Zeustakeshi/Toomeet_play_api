package com.toomeet.toomeet_play_api.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.toomeet.toomeet_play_api.dto.response.StudioVideoResponse;
import com.toomeet.toomeet_play_api.dto.video.VideoBasicInfo;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final Cloudinary cloudinary;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final ApplicationEventPublisher publisher;
    private final PlaylistService playlistService;
    private final ChannelService channelService;


    @Value("${cloudinary.dir_prefix}")
    private String dirPrefix;

    @Async
    @Override
    public void uploadVideo(String videoId, String userId, byte[] file) {

        Video video = Optional.ofNullable(videoRepository.findByVideoId(videoId))
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                    "resource_type", "video",
                    "folder", dirPrefix + "/videos",
                    "public_id", video.getVideoId(),
                    "allowed_formats", "mp4"
            ));

            video.setUrl(uploadResult.get("url").toString());
            video.setWidth(Long.parseLong(uploadResult.get("width").toString()));
            video.setHeight(Long.parseLong(uploadResult.get("height").toString()));
            video.setFormat(uploadResult.get("format").toString());
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
                    .userId(newVideo.getCreatedBy().getUserId())
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
    public Set<Video> getAllVideoFormPlaylist(String playlistId) {
        return videoRepository.findAllByPlaylistId(playlistId);
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
    public VideoBasicInfo getVideoBasicInfo(String videoId) {
        return videoRepository.getVideoBasicInfoByVideoId(videoId);
    }
}
