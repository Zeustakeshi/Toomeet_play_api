package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.enums.Authority;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.ChannelMapper;
import com.toomeet.toomeet_play_api.repository.ChannelRepository;
import com.toomeet.toomeet_play_api.service.AccountService;
import com.toomeet.toomeet_play_api.service.ChannelService;
import com.toomeet.toomeet_play_api.utils.ResourceUploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final AccountService accountService;
    private final ResourceUploader resourceUploader;

    @Override
    public ChannelGeneralResponse getChanelGeneralInfo(Account account) {

        Channel channel = Optional.ofNullable(account.getChannel())
                .orElseThrow(() -> new ApiException(ErrorCode.CHANNEL_NOT_FOUND));

        var channelResponse = channelMapper.toChannelGeneralResponse(channel);

        channelResponse.setVideoCount(channelRepository.getVideoCountByChannelId(channel.getChannelId()));

        return channelResponse;
    }

    @Override
    public Channel getChannelByOwnerId(String ownerId) {
        return channelRepository.findByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public ChannelGeneralResponse createChannel(CreateChannelRequest request, Account account) {

        if (accountService.existedChannel(account.getAccountId())) {
            throw new ApiException(ErrorCode.USER_ALREADY_A_OWNER_CHANNEL);
        }

        Channel channel = channelMapper.toChannel(request);

        account.addAuthority(Authority.CHANNEL_OWNER);
        accountService.updateAccount(account);

        channel.setAccount(account);

        Channel newChannel = channelRepository.save(channel);

        var channelResponse = channelMapper.toChannelGeneralResponse(newChannel);

        channelResponse.setVideoCount(channelRepository.getVideoCountByChannelId(channel.getChannelId()));

        return channelResponse;
    }

    @Override
    public String uploadChannelAvatar(MultipartFile image, Account account) {
        try {
            var uploaderResponse = resourceUploader.uploadImage(image.getInputStream().readAllBytes(), account.getAccountId(), "channel_avatar");
            String avatarUrl = uploaderResponse.getUrl();
            Channel channel = account.getChannel();
            channel.setAvatar(avatarUrl);
            channelRepository.save(channel);
            return avatarUrl;
        } catch (IOException e) {
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }

    }
}
