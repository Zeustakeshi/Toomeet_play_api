package com.toomeet.toomeet_play_api.service.channel.impl;

import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelDescriptionRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelNameRequest;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelAnalyticsResponse;
import com.toomeet.toomeet_play_api.dto.response.channel.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.event.UploadChannelAvatarEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.ChannelMapper;
import com.toomeet.toomeet_play_api.repository.ChannelRepository;
import com.toomeet.toomeet_play_api.service.channel.ChannelService;
import com.toomeet.toomeet_play_api.service.util.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ResourceService resourceService;
    private final ApplicationEventPublisher publisher;
    private final ChannelMapper channelMapper;

    @Override
    @Transactional
    public String updateChannelName(UpdateChannelNameRequest request, Account account) {
        Channel channel = channelRepository.findAllByAccountId(account.getId());
        channel.setName(request.getName());

        channelRepository.save(channel);

        return "Update channel name to \"" + request.getName() + "\" success.";
    }

    @Override
    @Transactional
    public String updateChannelDescription(UpdateChannelDescriptionRequest request, Account account) {
        Channel channel = channelRepository.findAllByAccountId(account.getId());
        channel.setDescription(request.getDescription());

        channelRepository.save(channel);

        return "Update channel description to \"" + request.getDescription() + "\" success.";
    }

    @Override
    public String updateChannelAvatar(MultipartFile avatar, Account account) {
        Channel channel = channelRepository.findAllByAccountId(account.getId());

        try {

            UploadChannelAvatarEvent uploadEvent = UploadChannelAvatarEvent
                    .builder()
                    .avatar(avatar.getInputStream().readAllBytes())
                    .channelId(channel.getId())
                    .userId(account.getUserId())
                    .build();

            publisher.publishEvent(uploadEvent);

            return "Your channel avatar has been successfully uploaded and is now pending processing.";

        } catch (Exception ex) {
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }

    }


    @Async
    @Override
    @Transactional
    public void updateChannelAvatarAsync(byte[] avatar, String channelId, String userId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ApiException(ErrorCode.CHANNEL_NOT_FOUND));

        try {
            ResourceUploaderResponse uploadResponse = resourceService
                    .uploadImage(
                            avatar,
                            channel.getId(),
                            "channel_avatars"
                    );

            String avatarUrl = uploadResponse.getUrl();

            channel.setAvatar(avatarUrl);

            channelRepository.save(channel);

        } catch (Exception ex) {
            // TODO: Send notification to user (userId)
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }

    }

    @Override
    public ChannelGeneralResponse getChannelGeneral(Account account) {
        Channel channel = channelRepository.findById(account.getChannelId())
                .orElseThrow(() -> new ApiException(ErrorCode.CHANNEL_NOT_FOUND));

        return channelMapper.toChannelGeneralResponse(channel);
    }

    @Override
    public ChannelAnalyticsResponse getChannelAnalytics(Account account) {
        String channelId = account.getChannelId();
        return channelRepository.getChannelAnalytics(channelId);
    }
}
