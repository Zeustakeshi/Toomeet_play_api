package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.request.video.*;
import com.toomeet.toomeet_play_api.dto.response.video.VideoResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoSmallResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    VideoResponse uploadVideo(MultipartFile video, Account account);

    void uploadVideoAsync(String videoId, String userId, byte[] video);

    VideoResponse getVideoById(String videoId, Account account);

    VideoResponse updateVideoMetadata(UpdateVideoMetadataRequest request, String videoId, Account account);

    VideoResponse updateVideoSettings(UpdateVideoSettingRequest request, String videoId, Account account);

    String uploadThumbnail(MultipartFile thumbnail, String videoId, Account account);

    void uploadThumbnailAsync(byte[] thumbnail, String videoId, String userId);

    String updateVideoTag(UpdateVideoTagRequest request, String videoId, Account account);

    String updateVideoCategory(UpdateVideoCategoryRequest request, String videoId, Account account);

    VideoResponse updateVideoDetails(UpdateVideoDetails request, String videoId, Account account);

    List<VideoSmallResponse> getTopVideo(int count, Account account);
}
