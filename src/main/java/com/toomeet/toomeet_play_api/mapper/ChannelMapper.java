package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.entity.Channel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    ChannelGeneralResponse toChannelGeneralResponse(Channel channel);
}
