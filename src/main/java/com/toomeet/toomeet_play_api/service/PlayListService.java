package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.PlayListResponse;
import com.toomeet.toomeet_play_api.entity.User;

public interface PlayListService {
    PlayListResponse createPlayList(CreatePlaylistRequest request, User user);
}
