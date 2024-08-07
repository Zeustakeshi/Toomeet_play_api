package com.toomeet.toomeet_play_api.service.playlist;

import com.toomeet.toomeet_play_api.dto.request.channel.AddVideoPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.DeleteVideoPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.NewPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.general.UpdateResponse;
import com.toomeet.toomeet_play_api.dto.response.playlist.PlaylistResponse;
import com.toomeet.toomeet_play_api.entity.Account;

public interface UserPlaylistService {

    PageableResponse<PlaylistResponse> getAllPlayList(int page, int limit, Account account);

    PlaylistResponse createPlaylist(NewPlaylistRequest request, Account account);

    String addVideoToPlaylist(AddVideoPlaylistRequest request, String playlistId, Account account);

    UpdateResponse<PlaylistResponse> updatePlaylist(UpdatePlaylistRequest request, String playlistId, Account account);

    PlaylistResponse getPlayListById(String playlistId, Account account);

    String deletePlaylist(String playlistId, Account account);

    String deleteVideo(DeleteVideoPlaylistRequest request, String playlistId, Account account);
}
