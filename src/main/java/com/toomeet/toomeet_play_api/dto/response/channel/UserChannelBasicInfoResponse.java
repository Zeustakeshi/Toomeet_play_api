/*
 *  UserChannelBasicInfoResponse
 *  @author: Minhhieuano
 *  @created 7/31/2024 8:54 PM
 * */


package com.toomeet.toomeet_play_api.dto.response.channel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class UserChannelBasicInfoResponse extends ChannelBasicInfoResponse {
    private final boolean subscribed;
    private final Long subscribeCount;
    private final boolean isMember;

    public UserChannelBasicInfoResponse(
            String id,
            String name,
            String avatar,
            Long subscribeCount,
            boolean subscribed,
            boolean isMember
    ) {
        super(id, name, avatar);
        this.subscribed = subscribed;
        this.subscribeCount = subscribeCount;
        this.isMember = isMember;
    }
}
