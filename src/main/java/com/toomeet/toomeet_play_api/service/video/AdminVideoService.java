package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.request.video.CreateVideoCategoryRequest;
import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;

public interface AdminVideoService {
    VideoCategoryResponse createCategory(CreateVideoCategoryRequest request);


}
