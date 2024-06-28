package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.PlaylistResponse;
import com.toomeet.toomeet_play_api.entity.Playlist;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.PlaylistMapper;
import com.toomeet.toomeet_play_api.repository.PlaylistRepository;
import com.toomeet.toomeet_play_api.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    @Override
    public PlaylistResponse createPlaylist(CreatePlaylistRequest request, String userId) {

        if (playlistRepository.existsByNameAndUserId(request.getName(), userId)) {
            throw new ApiException(ErrorCode.PLAYLIST_ALREADY_EXIST);
        }

        Playlist playlist = playlistRepository.save(playlistMapper.toPlaylist(request));
        return playlistMapper.toPlaylistResponse(playlist);
    }


    @Override
    public Playlist getByPlaylistId(String playlistId) {
        return playlistRepository.findByPlaylistId(playlistId);
    }

    @Override
    public List<PlaylistResponse> getAllPlayList(String userId) {
        return playlistRepository.findAllByUserId(userId)
                .stream().map(playlistMapper::toPlaylistResponse).toList();
    }

    @Override
    public Integer getPlaylistSize(String playlistId) {
        return playlistRepository.getPlaylistSize(playlistId);
    }


    @Override
    public boolean isPlaylistOwner(String playlistId, String userId) {
        return playlistRepository.isPlaylistOwner(playlistId, userId);
    }
}
