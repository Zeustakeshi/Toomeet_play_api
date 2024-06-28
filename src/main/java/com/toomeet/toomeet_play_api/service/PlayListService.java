package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.PlaylistResponse;
import com.toomeet.toomeet_play_api.entity.Playlist;

import java.util.List;

public interface PlaylistService {
    PlaylistResponse createPlaylist(CreatePlaylistRequest request, String userId);

    Playlist getByPlaylistId(String playlistId);

    List<PlaylistResponse> getAllPlayList(String userId);

    Integer getPlaylistSize(String playlistId);

    boolean isPlaylistOwner(String playlistId, String userId);
}
