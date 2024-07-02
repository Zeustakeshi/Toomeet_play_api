package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.UpdateVideoCategoryRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoMetadataRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoSettingRequest;
import com.toomeet.toomeet_play_api.dto.request.UpdateVideoTagRequest;
import com.toomeet.toomeet_play_api.dto.response.VideoResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    VideoResponse uploadVideo(MultipartFile video, Account account);

    void uploadVideoAsync(String videoId, String userId, byte[] video);

    VideoResponse getVideoById(String videoId, Account account);

    VideoResponse updateVideoMetadata(UpdateVideoMetadataRequest request, String videoId, Account account);

    VideoResponse updateVideoSettings(UpdateVideoSettingRequest request, String videoId, Account account);

    String uploadThumbnail(MultipartFile thumbnail, String videoId, Account account);

    String updateVideoTag(UpdateVideoTagRequest request, String videoId, Account account);

    String updateVideoCategory(UpdateVideoCategoryRequest request, String videoId, Account account);
    
}
