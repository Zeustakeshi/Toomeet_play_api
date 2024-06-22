package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.entity.User;

public interface ChannelService {
    ChannelGeneralResponse getChanelGeneralInfo(String channelId, User user);

    ChannelGeneralResponse createChannel(CreateChannelRequest request, String userId);


}
