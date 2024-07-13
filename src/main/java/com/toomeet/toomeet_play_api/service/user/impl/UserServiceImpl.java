package com.toomeet.toomeet_play_api.service.user.impl;

import com.toomeet.toomeet_play_api.dto.request.user.AddVideoHistoryRequest;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.Visibility;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.UserMapper;
import com.toomeet.toomeet_play_api.repository.UserRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final VideoRepository videoRepository;

    @Override
    public User getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public String addVideoHistory(AddVideoHistoryRequest request, Account account) {
        if (userRepository.existsVideoHistory(request.getVideoId(), account.getUserId())) {
            throw new ApiException(ErrorCode.VIDEO_ALREADY_SAVED_IN_HISTORY);
        }

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        if (video.getVisibility() != Visibility.PUBLIC) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }

        User user = userRepository.findById(account.getUserId()).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        user.getWatchedVideos().add(video);
        userRepository.save(user);
        return "Video has been added to watched list";
    }
}
