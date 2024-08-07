package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.request.video.*;
import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.general.UpdateResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoBasicInfoResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoSummaryResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import java.util.List;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public interface StudioVideoService {

    VideoResponse uploadVideo(MultipartFile video, Account account);

    void uploadVideoAsync(String videoId, String userId, byte[] video);

    VideoResponse getVideoById(String videoId, Account account);

    List<String> getVideoTags(String videoId, Account account);

    UpdateResponse<VideoResponse> updateVideoMetadata(
            UpdateVideoMetadataRequest request, String videoId, Account account);

    UpdateResponse<VideoResponse> updateVideoSettings(
            UpdateVideoSettingRequest request, String videoId, Account account);

    UpdateResponse<String> uploadThumbnail(MultipartFile thumbnail, String videoId, Account account);

    void uploadThumbnailAsync(byte[] thumbnail, String videoId, String userId);

    UpdateResponse<Set<String>> updateVideoTag(UpdateVideoTagRequest request, String videoId, Account account);

    UpdateResponse<String> updateVideoCategory(UpdateVideoCategoryRequest request, String videoId, Account account);

    UpdateResponse<VideoResponse> updateVideoDetails(UpdateVideoDetails request, String videoId, Account account);

    List<VideoBasicInfoResponse> getTopVideo(int count, Account account);

    PageableResponse<VideoSummaryResponse> getAllVideo(int page, int limit, Account account);

    String deleteVideo(String videoId, Account account);

    void deleteVideoResourceAsync(String videoId);

    List<VideoCategoryResponse> getAllCategory();
}
