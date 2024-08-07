package com.toomeet.toomeet_play_api.service.channel;

import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelDescriptionRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelNameRequest;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelAnalyticsResponse;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.dto.response.general.UpdateResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface StudioChannelService {
    UpdateResponse<String> updateChannelName(UpdateChannelNameRequest request, Account account);

    UpdateResponse<String> updateChannelDescription(UpdateChannelDescriptionRequest request, Account account);

    UpdateResponse<String> updateChannelAvatar(MultipartFile avatar, Account account);

    void updateChannelAvatarAsync(byte[] avatar, String channelId, String userId);

    ChannelBasicInfoResponse getChannelGeneral(Account account);

    ChannelAnalyticsResponse getChannelAnalytics(Account account);
}
