package com.toomeet.toomeet_play_api.service.channel;

import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelNameRequest;
import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface ChannelService {
    String updateChannelName(UpdateChannelNameRequest request, Account account);

    String updateChannelAvatar(MultipartFile avatar, Account account);

    void updateChannelAvatarAsync(byte[] avatar, String channelId, String userId);
}
