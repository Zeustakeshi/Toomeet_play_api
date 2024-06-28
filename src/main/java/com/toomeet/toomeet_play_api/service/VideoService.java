package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.response.StudioVideoResponse;
import com.toomeet.toomeet_play_api.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface VideoService {
    void uploadVideo(String videoId, String userId, byte[] file);

    StudioVideoResponse createVideo(MultipartFile file, String userId);

    Video getVideoByVideoId(String videoId);

    Set<StudioVideoResponse> getAllVideoFormPlaylist(String playlistId);

    String addToPlaylist(String videoId, String playlistId, String userId);

    Set<StudioVideoResponse> getAllVideoByOwner(String userId);


}
