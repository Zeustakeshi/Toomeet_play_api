package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.repository.ChannelRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ChannelRepository.class})
public interface ChannelMapper {
    ChannelBasicInfoResponse toChannelGeneralResponse(Channel channel);
}
