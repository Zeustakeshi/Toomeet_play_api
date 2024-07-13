package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.channel.PlaylistResponse;
import com.toomeet.toomeet_play_api.entity.Playlist;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    @Mapping(source = "channel.name", target = "owner.name")
    @Mapping(source = "channel.avatar", target = "owner.avatar")
    @Mapping(source = "channel.id", target = "owner.id")
    @Mapping(source = "videos", target = "totalVideo")
    PlaylistResponse toPlaylistResponse(Playlist playlist);

    default int countVideo(Set<Video> videos) {
        return videos.size();
    }
}
