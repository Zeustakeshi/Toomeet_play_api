package com.toomeet.toomeet_play_api.service.channel.impl;

import com.toomeet.toomeet_play_api.repository.channel.ChannelRepository;
import com.toomeet.toomeet_play_api.service.channel.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Override
    public Long getChannelSubscriberCount(String channelId) {
        return channelRepository.countSubscriber(channelId);
    }
}
