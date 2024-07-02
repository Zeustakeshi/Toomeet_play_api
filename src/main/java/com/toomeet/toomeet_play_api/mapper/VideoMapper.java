package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.VideoResponse;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    VideoResponse toVideoResponse(Video video);
}
