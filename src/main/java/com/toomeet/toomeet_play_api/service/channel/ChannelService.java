package com.toomeet.toomeet_play_api.service.channel;

import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelDescriptionRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelNameRequest;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelAnalyticsResponse;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface ChannelService {

    Long getChannelSubscriberCount(String channelId);

    String updateChannelName(UpdateChannelNameRequest request, Account account);

    String updateChannelDescription(UpdateChannelDescriptionRequest request, Account account);

    String updateChannelAvatar(MultipartFile avatar, Account account);

    void updateChannelAvatarAsync(byte[] avatar, String channelId, String userId);

    ChannelBasicInfoResponse getChannelGeneral(Account account);

    ChannelAnalyticsResponse getChannelAnalytics(Account account);
}
