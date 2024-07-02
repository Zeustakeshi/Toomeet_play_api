package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.CreateVideoCategoryRequest;
import com.toomeet.toomeet_play_api.dto.response.VideoCategoryResponse;

public interface AdminVideoService {
    VideoCategoryResponse createCategory(CreateVideoCategoryRequest request);
}
