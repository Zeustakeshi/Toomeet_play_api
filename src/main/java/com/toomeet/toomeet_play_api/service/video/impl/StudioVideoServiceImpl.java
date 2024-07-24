package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.request.video.*;
import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoBasicInfoResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoSummaryResponse;
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
import com.toomeet.toomeet_play_api.event.UploadVideoThumbnailEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.PageMapper;
import com.toomeet.toomeet_play_api.mapper.VideoMapper;
import com.toomeet.toomeet_play_api.repository.video.CategoryRepository;
import com.toomeet.toomeet_play_api.repository.video.StudioVideoRepository;
import com.toomeet.toomeet_play_api.repository.video.TagRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.util.NanoIdService;
import com.toomeet.toomeet_play_api.service.util.ResourceService;
import com.toomeet.toomeet_play_api.service.video.StudioVideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.toomeet.toomeet_play_api.enums.ResourceUploadStatus.FAIL;
import static com.toomeet.toomeet_play_api.enums.ResourceUploadStatus.PROCESSING;

@Service
@RequiredArgsConstructor
public class StudioVideoServiceImpl implements StudioVideoService {
    private final ResourceService resourceService;
    private final NanoIdService nanoIdService;
    private final VideoRepository videoRepository;
    private final ApplicationEventPublisher publisher;
    private final VideoMapper videoMapper;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final PageMapper pageMapper;
    private final StudioVideoRepository studioVideoRepository;


    @Override
    @SneakyThrows
    public VideoResponse uploadVideo(MultipartFile file, Account account) {

        Channel channel = account.getChannel();

        Video video = Video.builder()
                .allowedComment(false)
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
            video.setAllowedComment(true);
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
        return studioVideoRepository.getVideoByIdAndChannelId(videoId, account.getChannelId())
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));
    }

    @Override
    public String uploadThumbnail(MultipartFile thumbnail, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);
        try {

            UploadVideoThumbnailEvent uploadEvent = UploadVideoThumbnailEvent.builder()
                    .thumbnail(thumbnail.getInputStream().readAllBytes())
                    .userId(account.getUserId())
                    .videoId(video.getId())
                    .build();

            publisher.publishEvent(uploadEvent);

            return "Your video thumbnail has been successfully uploaded and is now pending processing.";
        } catch (Exception ex) {
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }

    }

    @Async
    @Override
    @Transactional
    public void uploadThumbnailAsync(byte[] thumbnail, String videoId, String userId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));
        try {
            ResourceUploaderResponse uploadResponse = resourceService.uploadImage(thumbnail, videoId, "video_thumbnails");
            String thumbnailUrl = uploadResponse.getUrl();
            video.setThumbnail(thumbnailUrl);
            videoRepository.save(video);

        } catch (Exception ex) {
            // TODO: send notification upload thumbnail failed for user (userId)
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }

    }

    @Override
    @Transactional
    public String updateVideoTag(UpdateVideoTagRequest request, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);
        updateVideoTag(video, request.getTags());
        return "Video tag has been updated";
    }

    @Override
    @Transactional
    public String updateVideoCategory(UpdateVideoCategoryRequest request, String videoId, Account account) {
        Video video = getVideoByIdWithOwnershipCheck(videoId, account);
        updateVideoCategory(video, request.getCategory());
        videoRepository.save(video);
        return "update video category successful";
    }

    private void updateVideoVisibility(Video video, Visibility visibility) {
        if (visibility == Visibility.PUBLIC) publicVideo(video);
        else if (visibility == Visibility.MEMBER) publicVideoMember(video);
        else video.setVisibility(visibility);
    }

    @Override
    @Transactional
    public VideoResponse updateVideoDetails(UpdateVideoDetails request, String videoId, Account account) {

        Video video = getVideoByIdWithOwnershipCheck(videoId, account);

        video.setAllowedComment(request.isAllowedComment());
        video.setForKid(request.isVideoForKid());
        video.setLanguage(request.getLanguage());
        video.setRecordDate(request.getRecordDate());

        updateVideoCategory(video, request.getCategory());
        updateVideoTag(video, request.getTags());
        updateVideoVisibility(video, request.getVisibility());

        videoRepository.save(video);
        return videoMapper.toVideoResponse(video);
    }

    @Override
    public List<VideoBasicInfoResponse> getTopVideo(int count, Account account) {
        System.out.println(count);
        PageRequest pageRequest = PageRequest.of(0, count);
        Page<Video> topVideos = studioVideoRepository.getTopVideoByChannelId(account.getChannelId(), pageRequest);
        return topVideos.map(videoMapper::toVideoBasicInfoResponse).stream().toList();
    }

    @Override
    public PageableResponse<VideoSummaryResponse> getAllVideo(int page, int limit, Account account) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<VideoSummaryResponse> videos = studioVideoRepository.getAllSummaryByChannelId(account.getChannelId(), pageRequest);
        return pageMapper.toPageableResponse(videos);
    }

    @Override
    public List<String> getVideoTags(String videoId, Account account) {
        if (!studioVideoRepository.isOwner(account.getChannelId(), videoId)) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }
        return tagRepository.getAllByVideoId(videoId).stream().map(Tag::getName).toList();
    }

    private void updateVideoCategory(Video video, String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
        video.setCategory(category);
    }

    private void updateVideoTag(Video video, Set<String> tags) {

        tagRepository.deleteAllByVideoId(video.getId());

        List<Tag> newTags = new ArrayList<>();

        for (var tagName : tags) {
            Tag tag = Tag.builder()
                    .name(tagName)
                    .video(video)
                    .build();
            newTags.add(tag);
        }

        tagRepository.saveAll(newTags);
    }

    private void publicVideo(Video video) {
        if (isPublicVideo(video)) return;
        if (canNotPublicVideo(video)) {
            throw new ApiException(ErrorCode.PUBLIC_VIDEO_ERROR);
        }
        video.setVisibility(Visibility.PUBLIC);
    }

    private void publicVideoMember(Video video) {
        if (isMemberVideo(video)) return;
        if (canNotPublicVideo(video) || canNotPublicMemberVideo(video)) {
            throw new ApiException(ErrorCode.PUBLIC_VIDEO_ERROR);
        }
        video.setVisibility(Visibility.MEMBER);
    }

    private boolean canNotPublicMemberVideo(Video video) {
        return false;
    }

    private boolean isPublicVideo(Video video) {
        return video.getVisibility() == Visibility.PUBLIC;
    }

    private boolean isMemberVideo(Video video) {
        return video.getVisibility() == Visibility.MEMBER;
    }

    private boolean canNotPublicVideo(Video video) {
        if (video.getThumbnail() == null) return true;
        if (video.getCategory() == null) return true;
        if (tagRepository.countByVideoId(video.getId()) < 4) return true;
        return video.getUploadStatus() == FAIL || video.getUploadStatus() == PROCESSING;
    }


}

