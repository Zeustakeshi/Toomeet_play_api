package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;
import org.springframework.web.multipart.MultipartFile;

public interface ChannelService {
    ChannelGeneralResponse getChanelGeneralInfo(Account account);

    ChannelGeneralResponse createChannel(CreateChannelRequest request, Account account);

    Channel getChannelByOwnerId(String ownerId);

    String uploadChannelAvatar(MultipartFile image, Account account);

}
