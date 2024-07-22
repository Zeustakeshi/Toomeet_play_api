package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.video.*;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = {ChannelMapper.class})
public interface VideoMapper {

    VideoResponse toVideoResponse(Video video);

    VideoPreviewResponse toVideoPreviewResponse(Video video);

    VideoWatchResponse toVideoWatchResponse(Video video);

    VideoSmallResponse toVideoSmallResponse(Video video);

    StudioVideoSummaryResponse toStudioVideoSummaryResponse(Video video);

}
