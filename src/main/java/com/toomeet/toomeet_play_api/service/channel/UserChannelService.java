/*
 *  UserChannelService
 *  @author: Minhhieuano
 *  @created 7/31/2024 10:52 PM
 * */


package com.toomeet.toomeet_play_api.service.channel;

import com.toomeet.toomeet_play_api.dto.response.channel.UserChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.entity.Account;

public interface UserChannelService {
    UserChannelBasicInfoResponse getBasicInfo(String channelId, Account account);

    String subscribe(String channelId, Account account);

    String unsubscribe(String channelId, Account account);
}
