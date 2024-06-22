package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.PlayListResponse;
import com.toomeet.toomeet_play_api.entity.Playlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    PlayListResponse toPlayListResponse(Playlist playlist);

    Playlist toPlayList(CreatePlaylistRequest request);
}
