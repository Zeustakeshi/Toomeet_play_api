package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.entity.video.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoCategoryMapper {
    VideoCategoryResponse toCategoryResponse(Category category);
}
