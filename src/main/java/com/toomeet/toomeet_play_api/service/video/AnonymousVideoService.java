package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoDetailPublicResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoPreviewResponse;

import java.util.List;

public interface AnonymousVideoService {

    PageableResponse<VideoPreviewResponse> getAllVideo(int page, int limit);

    VideoDetailPublicResponse getVideoDetails(String videoId);

    List<VideoCategoryResponse> getAllCategory();
}
