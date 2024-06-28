package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.StudioVideoResponse;
import com.toomeet.toomeet_play_api.entity.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    StudioVideoResponse toVideoResponse(Video video);
}
