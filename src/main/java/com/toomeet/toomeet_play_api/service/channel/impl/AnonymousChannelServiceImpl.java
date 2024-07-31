/*
 *  AnonymousChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 8/1/2024 12:04 AM
 * */


package com.toomeet.toomeet_play_api.service.channel.impl;

import com.toomeet.toomeet_play_api.dto.response.channel.AnonymousChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.repository.channel.AnonymousChannelRepository;
import com.toomeet.toomeet_play_api.service.channel.AnonymousChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnonymousChannelServiceImpl implements AnonymousChannelService {

    private final AnonymousChannelRepository channelRepository;

    @Override
    public AnonymousChannelBasicInfoResponse getBasicInfo(String channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new ApiException(ErrorCode.CHANNEL_NOT_FOUND);
        }
        return channelRepository.getBasicInfo(channelId);
    }
}
