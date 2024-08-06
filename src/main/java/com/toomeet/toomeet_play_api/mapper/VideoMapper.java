package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.video.*;
import com.toomeet.toomeet_play_api.dto.video.VideoDetailDto;
import com.toomeet.toomeet_play_api.dto.video.VideoNewsfeedDto;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ChannelMapper.class})
public interface VideoMapper {

    VideoBasicInfoResponse toVideoBasicInfoResponse(Video video);

    VideoResponse toVideoResponse(Video video);

    VideoNewsfeedResponse toVideoPreviewResponse(VideoNewsfeedDto video);

    @Mapping(source = "channelId", target = "channelId")
    UserVideoDetailResponse toUserVideoDetailResponse(VideoDetailDto video);

    @Mapping(source = "channelId", target = "channelId")
    AnonymousVideoDetailResponse toAnonymousVideoDetailResponse(VideoDetailDto video);
}
