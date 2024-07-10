package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.video.StudioVideoSummaryResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoPreviewResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoSmallResponse;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    VideoResponse toVideoResponse(Video video);

    VideoPreviewResponse toVideoPreviewResponse(Video video);

    VideoSmallResponse toVideoSmallResponse(Video video);

    StudioVideoSummaryResponse toStudioVideoSummaryResponse(Video video);
}
