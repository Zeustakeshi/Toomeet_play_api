/*
 *  AnonymousChannelBasicInfoResponse
 *  @author: Minhhieuano
 *  @created 7/31/2024 7:31 PM
 * */

package com.toomeet.toomeet_play_api.dto.response.channel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class AnonymousChannelBasicInfoResponse extends ChannelBasicInfoResponse {
    private final Long subscribeCount;

    public AnonymousChannelBasicInfoResponse(String id, String name, String avatar, Long subscribeCount) {
        super(id, name, avatar);
        this.subscribeCount = subscribeCount;
    }
}
