package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;

public interface ChannelService {
    ChannelGeneralResponse getChanelGeneralInfo(String channelId, Account account);

    ChannelGeneralResponse createChannel(CreateChannelRequest request, String userId);

    Channel getChannelByOwnerId(String ownerId);


}
