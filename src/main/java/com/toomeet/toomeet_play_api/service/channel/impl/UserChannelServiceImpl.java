/*
 *  UserChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 8/1/2024 12:38 AM
 * */


package com.toomeet.toomeet_play_api.service.channel.impl;

import com.toomeet.toomeet_play_api.dto.response.channel.UserChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.repository.channel.UserChannelRepository;
import com.toomeet.toomeet_play_api.service.channel.UserChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChannelServiceImpl implements UserChannelService {

    private final UserChannelRepository channelRepository;

    @Override
    public UserChannelBasicInfoResponse getBasicInfo(String channelId, Account account) {
        if (!channelRepository.existsById(channelId)) {
            throw new ApiException(ErrorCode.CHANNEL_NOT_FOUND);
        }
        return channelRepository.getBasicInfo(channelId, account.getUserId());
    }

    @Override
    public String subscribe(String channelId, Account account) {
        return "";
    }

    @Override
    public String unsubscribe(String channelId, Account account) {
        return "";
    }
}
