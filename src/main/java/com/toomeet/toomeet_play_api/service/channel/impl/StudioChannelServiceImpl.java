/*
 *  StudioChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 7/31/2024 10:53 PM
 * */

package com.toomeet.toomeet_play_api.service.channel.impl;

import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelDescriptionRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelNameRequest;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelAnalyticsResponse;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelBasicInfoResponse;
import com.toomeet.toomeet_play_api.dto.response.general.UpdateResponse;
import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.event.UploadChannelAvatarEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.ChannelMapper;
import com.toomeet.toomeet_play_api.repository.channel.StudioChannelRepository;
import com.toomeet.toomeet_play_api.service.channel.StudioChannelService;
import com.toomeet.toomeet_play_api.service.util.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StudioChannelServiceImpl implements StudioChannelService {

    private final StudioChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final ResourceService resourceService;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public UpdateResponse<String> updateChannelName(UpdateChannelNameRequest request, Account account) {
        Channel channel = channelRepository.findByAccountId(account.getId());
        channel.setName(request.getName());
        channelRepository.save(channel);
        return UpdateResponse.success(request.getName());
    }

    @Override
    @Transactional
    public UpdateResponse<String> updateChannelDescription(UpdateChannelDescriptionRequest request, Account account) {
        Channel channel = channelRepository.findByAccountId(account.getId());
        channel.setDescription(request.getDescription());

        channelRepository.save(channel);

        return UpdateResponse.success(request.getDescription());
    }

    @Override
    public UpdateResponse<String> updateChannelAvatar(MultipartFile avatar, Account account) {
        Channel channel = channelRepository.findByAccountId(account.getId());

        try {

            UploadChannelAvatarEvent uploadEvent = UploadChannelAvatarEvent.builder()
                    .avatar(avatar.getInputStream().readAllBytes())
                    .channelId(channel.getId())
                    .userId(account.getUserId())
                    .build();

            publisher.publishEvent(uploadEvent);

            return UpdateResponse.pending("Your channel avatar has been successfully uploaded and is now pending processing.");

        } catch (Exception ex) {
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }
    }

    @Async
    @Override
    @Transactional
    public void updateChannelAvatarAsync(byte[] avatar, String channelId, String userId) {
        Channel channel =
                channelRepository.findById(channelId).orElseThrow(() -> new ApiException(ErrorCode.CHANNEL_NOT_FOUND));

        try {
            ResourceUploaderResponse uploadResponse =
                    resourceService.uploadImage(avatar, channel.getId(), "channel_avatars");

            String avatarUrl = uploadResponse.getUrl();

            channel.setAvatar(avatarUrl);

            channelRepository.save(channel);

        } catch (Exception ex) {
            // TODO: Send notification to user (userId)
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }
    }

    @Override
    public ChannelBasicInfoResponse getChannelGeneral(Account account) {
        Channel channel = channelRepository
                .findById(account.getChannelId())
                .orElseThrow(() -> new ApiException(ErrorCode.CHANNEL_NOT_FOUND));
        return channelMapper.toChannelGeneralResponse(channel);
    }

    @Override
    public ChannelAnalyticsResponse getChannelAnalytics(Account account) {
        String channelId = account.getChannelId();
        return channelRepository.getChannelAnalytics(channelId);
    }
}
