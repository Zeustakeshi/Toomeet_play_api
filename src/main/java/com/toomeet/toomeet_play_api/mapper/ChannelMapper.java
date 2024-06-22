package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.entity.Channel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    ChannelGeneralResponse toChannelGeneralResponse(Channel channel);

    Channel toChannel(CreateChannelRequest request);
}
