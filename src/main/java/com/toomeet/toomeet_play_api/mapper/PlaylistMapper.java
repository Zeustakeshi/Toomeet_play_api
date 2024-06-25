package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.PlaylistResponse;
import com.toomeet.toomeet_play_api.entity.Playlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    PlaylistResponse toPlaylistResponse(Playlist playlist);

    Playlist toPlaylist(CreatePlaylistRequest request);
}
