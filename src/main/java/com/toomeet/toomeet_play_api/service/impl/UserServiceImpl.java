package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.response.UserAuthenticationResponse;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.mapper.UserMapper;
import com.toomeet.toomeet_play_api.repository.UserRepository;
import com.toomeet.toomeet_play_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existedByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserAuthenticationResponse getUserAuthentication(User user) {
        return userMapper.toUserAuthenticationResponse(user);
    }
}
