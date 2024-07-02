package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.request.UpdateVideoCategoryRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoMetadataRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoSettingRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoTagRequest;
import com.toomeet.toomeet_play_api.dto.response.VideoResponse;
import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.entity.video.Category;
import com.toomeet.toomeet_play_api.entity.video.Tag;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.ResourceUploadStatus;
import com.toomeet.toomeet_play_api.enums.Visibility;
import com.toomeet.toomeet_play_api.event.UploadVideoEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.video.CategoryRepository;
import com.toomeet.toomeet_play_api.repository.video.TagRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.NanoIdService;
import com.toomeet.toomeet_play_api.service.ResourceService;
import com.toomeet.toomeet_play_api.service.VideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.toomeet.toomeet_play_api.enums.ResourceUploadStatus.FAIL;
import static com.toomeet.toomeet_play_api.enums.ResourceUploadStatus.PROCESSING;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final ResourceService resourceService;
    private final NanoIdService nanoIdService;
    private final VideoRepository videoRepository;
    private final ApplicationEventPublisher publisher;
    private final VideoMapper videoMapper;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @SneakyThrows
    public VideoResponse uploadVideo(MultipartFile file, Account account) {

        Channel channel = account.getChannel();

        Video video = Video.builder()
                .allowedComment(true)
                .title("Untitled Videos-" + nanoIdService.generateCustomNanoId(12))
                .channel(channel)
                .description("No description")
                .build();

        Video newVideo = videoRepository.save(video);

        UploadVideoEvent uploadEvent = UploadVideoEvent.builder()
                .videoId(newVideo.getId())
                .userId(account.getUserId())
                .video(file.getInputStream().readAllBytes())
                .build();

        publisher.publishEvent(uploadEvent);

        return videoMapper.toVideoResponse(newVideo);
    }

    @Async
    @Override
    public void uploadVideoAsync(String videoId, String userId, byte[] file) {
        Video video = videoRepository
                .findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        try {
            ResourceUploaderResponse uploadResponse = resourceService.uploadVideo(file, videoId, "/videos");
            video.setUploadStatus(ResourceUploadStatus.SUCCESS);
            video.setUrl(uploadResponse.getUrl());
            video.setWidth(uploadResponse.getWidth());
            video.setHeight(uploadResponse.getHeight());
            //TODO: send notify video upload success to user (userId)
        } catch (Exception ex) {
            video.setUploadStatus(FAIL);
            // TODO: send notify video upload failed to user (userId)
        }

        videoRepository.save(video);

    }

    @Override
    @Transactional
    public VideoResponse updateVideoMetadata(UpdateVideoMetadataRequest request, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setRecordeDate(request.getRecordeDate());
        video.setLanguage(request.getLanguage());
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    @Override
    @Transactional
    public VideoResponse updateVideoSettings(UpdateVideoSettingRequest request, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);
        video.setAllowedComment(request.isAllowedComment());
        updateVideoVisibility(video, request.getVisibility());
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    @Override
    public VideoResponse getVideoById(String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);
        return videoMapper.toVideoResponse(video);
    }

    private Video getVideoByIdWithOwnershipCheck(String videoId, Account account) {
        Optional<Video> optionalVideo = videoRepository.findById(videoId);

        Video video = optionalVideo.orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));
        if (!videoRepository.isOwner(account.getUserId(), videoId)) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }
        return video;
    }

    @Override
    public String uploadThumbnail(MultipartFile thumbnail, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);
        try {
            ResourceUploaderResponse uploadResponse = resourceService.uploadImage(thumbnail.getInputStream().readAllBytes(), videoId, "video_thumbnails");
            String thumbnailUrl = uploadResponse.getUrl();
            video.setThumbnail(thumbnailUrl);
            videoRepository.save(video);
            return thumbnailUrl;
        } catch (Exception ex) {
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }

    }

    @Override
    @Transactional
    public String updateVideoTag(UpdateVideoTagRequest request, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);

        tagRepository.deleteAllByVideoId(videoId);

        List<Tag> tags = new ArrayList<>();

        for (var tagName : request.getTags()) {
            Tag tag = Tag.builder()
                    .name(tagName)
                    .video(video)
                    .build();
            tags.add(tag);
        }

        tagRepository.saveAll(tags);
        return "Video tag has been updated";
    }

    @Override
    @Transactional
    public String updateVideoCategory(UpdateVideoCategoryRequest request, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);

        Category category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
        video.setCategory(category);

        videoRepository.save(video);

        return "update video category successful";
    }

    private void updateVideoVisibility(Video video, Visibility visibility) {
        if (visibility == Visibility.PUBLIC) publicVideo(video);
        else if (visibility == Visibility.MEMBER) publicVideoMember(video);
        else video.setVisibility(visibility);
    }

    private void publicVideo(Video video) {
        if (isPublicVideo(video)) return;
        if (!canPublicVideo(video)) {
            throw new ApiException(ErrorCode.PUBLIC_VIDEO_ERROR);
        }
        video.setVisibility(Visibility.PUBLIC);
    }

    private void publicVideoMember(Video video) {
        if (isMemberVideo(video)) return;
        if (!canPublicVideo(video) || !canPublicMemberVideo(video)) {
            throw new ApiException(ErrorCode.PUBLIC_VIDEO_ERROR);
        }
        video.setVisibility(Visibility.MEMBER);
    }

    private boolean canPublicMemberVideo(Video video) {
        return true;
    }

    private boolean isPublicVideo(Video video) {
        return video.getVisibility() == Visibility.PUBLIC;
    }

    private boolean isMemberVideo(Video video) {
        return video.getVisibility() == Visibility.MEMBER;
    }

    private boolean canPublicVideo(Video video) {
        if (video.getCategory() == null) return false;
        if (tagRepository.countByVideoId(video.getId()) < 5) return false;
        if (video.getUploadStatus() == FAIL || video.getUploadStatus() == PROCESSING) return false;
        return true;
    }
}

