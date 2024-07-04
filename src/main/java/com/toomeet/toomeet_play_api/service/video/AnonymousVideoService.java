package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoPreviewResponse;

import java.util.List;

public interface AnonymousVideoService {
    List<VideoPreviewResponse> getAllVideo();

    List<VideoCategoryResponse> getAllCategory();
}
