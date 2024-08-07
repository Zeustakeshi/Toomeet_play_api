/*
 *  AnonymousChannelService
 *  @author: Minhhieuano
 *  @created 7/31/2024 11:53 PM
 * */

package com.toomeet.toomeet_play_api.service.channel;

import com.toomeet.toomeet_play_api.dto.response.channel.AnonymousChannelBasicInfoResponse;

public interface AnonymousChannelService {
    AnonymousChannelBasicInfoResponse getBasicInfo(String channelId);
}
