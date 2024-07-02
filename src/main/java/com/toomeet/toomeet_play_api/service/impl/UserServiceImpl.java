package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.UserMapper;
import com.toomeet.toomeet_play_api.repository.UserRepository;
import com.toomeet.toomeet_play_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public User getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }
}
