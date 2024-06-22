package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.request.CreateChannelRequest;
import com.toomeet.toomeet_play_api.dto.response.ChannelGeneralResponse;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.Authority;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.ChannelMapper;
import com.toomeet.toomeet_play_api.mapper.UserMapper;
import com.toomeet.toomeet_play_api.repository.ChannelRepository;
import com.toomeet.toomeet_play_api.service.ChannelService;
import com.toomeet.toomeet_play_api.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserService userService;
    private final ChannelMapper channelMapper;
    private final UserMapper userMapper;

    @Override
    public ChannelGeneralResponse getChanelGeneralInfo(String channelId, User user) {

        Channel channel = Optional.ofNullable(channelRepository.findByChannelId(channelId))
                .orElseThrow(() -> new ApiException(ErrorCode.CHANNEL_NOT_FOUND));

        if (!channel.getOwner().getUserId().equals(user.getUserId())) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }

        var channelResponse = channelMapper.toChannelGeneralResponse(channel);
        channelResponse.setOwner(userMapper.toUserAuthenticationResponse(user));

        return channelResponse;
    }


    @Override
    @Transactional
    public ChannelGeneralResponse createChannel(CreateChannelRequest request, String userId) {

        User user = Optional.ofNullable(userService.getUserByUserId(userId))
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (user.getChannel() != null) {
            throw new ApiException(ErrorCode.USER_ALREADY_A_OWNER_CHANNEL);
        }

        Channel channel = channelMapper.toChannel(request);

        user.addAuthority(Authority.CHANNEL_OWNER);
        channel.setOwner(user);

        Channel newChannel = channelRepository.save(channel);

        var channelResponse = channelMapper.toChannelGeneralResponse(newChannel);
        channelResponse.setOwner(userMapper.toUserAuthenticationResponse(user));
        return channelResponse;
    }
}
