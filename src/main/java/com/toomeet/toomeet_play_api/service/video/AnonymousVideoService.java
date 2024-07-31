package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.AnonymousVideoDetailResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoNewsfeedResponse;

public interface AnonymousVideoService {

    PageableResponse<VideoNewsfeedResponse> getNewsfeeds(int page, int limit);

    AnonymousVideoDetailResponse getVideoDetails(String videoId);

//    List<VideoCategoryResponse> getAllCategory();
}








