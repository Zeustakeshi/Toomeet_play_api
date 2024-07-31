package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto;
import com.toomeet.toomeet_play_api.dto.response.playlist.PlaylistResponse;
import com.toomeet.toomeet_play_api.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChannelMapper.class})
public interface PlaylistMapper {

    PlaylistResponse toPlaylistResponse(Playlist playlist);

    @Mapping(source = "channel", target = "owner")
    PlaylistResponse toPlaylistResponse(PlaylistTotalVideoDto playlistTotalVideoDto);

}
