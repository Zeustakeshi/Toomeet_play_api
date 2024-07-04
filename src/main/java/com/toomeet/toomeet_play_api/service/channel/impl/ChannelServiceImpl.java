package com.toomeet.toomeet_play_api.service.channel.impl;

import com.toomeet.toomeet_play_api.dto.request.channel.UpdateChannelNameRequest;
import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.repository.ChannelRepository;
import com.toomeet.toomeet_play_api.service.channel.ChannelService;
import com.toomeet.toomeet_play_api.service.util.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ResourceService resourceService;


    @Override
    @Transactional
    public String updateChannelName(UpdateChannelNameRequest request, Account account) {
        Channel channel = channelRepository.findAllByAccountId(account.getId());
        channel.setName(request.getName());

        channelRepository.save(channel);

        return "Update channel name to \"" + request.getName() + "\" success.";
    }


    @Override
    public String updateChannelAvatar(MultipartFile avatar, Account account) {
        Channel channel = channelRepository.findAllByAccountId(account.getId());

        try {
            ResourceUploaderResponse uploadResponse = resourceService
                    .uploadImage(
                            avatar.getInputStream().readAllBytes(),
                            channel.getId(),
                            "channel_avatars"
                    );

            String avatarUrl = uploadResponse.getUrl();

            channel.setAvatar(avatarUrl);

            channelRepository.save(channel);

            return avatarUrl;

        } catch (Exception ex) {
            throw new ApiException(ErrorCode.UPLOAD_IMAGE_EXCEPTION);
        }

    }
}
