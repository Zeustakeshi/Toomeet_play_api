/*
 *  AnonymousPlaylistServiceImpl
 *  @author: Minhhieuano
 *  @created 8/7/2024 1:07 PM
 * */

package com.toomeet.toomeet_play_api.service.playlist.impl;

import com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto;
import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.playlist.PlaylistResponse;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.PageMapper;
import com.toomeet.toomeet_play_api.mapper.PlaylistMapper;
import com.toomeet.toomeet_play_api.repository.playlist.AnonymousPlaylistRepository;
import com.toomeet.toomeet_play_api.service.playlist.AnonymousPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnonymousPlaylistServiceImpl implements AnonymousPlaylistService {
    private final AnonymousPlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final PageMapper pageMapper;

    @Override
    public PlaylistResponse getPlaylistById(String playlistId) {
        PlaylistTotalVideoDto playlist = playlistRepository
                .findPlaylistById(playlistId)
                .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));
        return playlistMapper.toPlaylistResponse(playlist);
    }

    @Override
    public PageableResponse<PlaylistResponse> getAllPlayListByChannelId(String channelId, int page, int limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        Page<PlaylistTotalVideoDto> playlistPage =
                playlistRepository.findAllByChannelId(channelId, PageRequest.of(page, limit, sort));
        return pageMapper.toPageableResponse(playlistPage.map(playlistMapper::toPlaylistResponse));
    }
}
