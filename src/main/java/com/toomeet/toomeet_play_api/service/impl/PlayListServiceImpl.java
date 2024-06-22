package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.PlayListResponse;
import com.toomeet.toomeet_play_api.entity.Playlist;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.PlaylistMapper;
import com.toomeet.toomeet_play_api.repository.PlaylistRepository;
import com.toomeet.toomeet_play_api.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    @Override
    public PlayListResponse createPlayList(CreatePlaylistRequest request, User user) {

        if (playlistRepository.existsByNameAndCreatedByUserId(request.getName(), user.getUserId())) {
            throw new ApiException(ErrorCode.PLAYLIST_ALREADY_EXIST);
        }

        Playlist playlist = playlistRepository.save(playlistMapper.toPlayList(request));
        return playlistMapper.toPlayListResponse(playlist);
    }


}
