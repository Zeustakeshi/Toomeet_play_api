package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.video.VideoBasicInfoResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoDetailPublicResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoPreviewResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoResponse;
import com.toomeet.toomeet_play_api.dto.video.VideoDetailPublicDto;
import com.toomeet.toomeet_play_api.dto.video.VideoPreviewDto;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ChannelMapper.class})
public interface VideoMapper {

    VideoBasicInfoResponse toVideoBasicInfoResponse(Video video);

    VideoResponse toVideoResponse(Video video);

    VideoPreviewResponse toVideoPreviewResponse(VideoPreviewDto video);

    VideoDetailPublicResponse toVideoDetailPublicResponse(VideoDetailPublicDto video);
}
