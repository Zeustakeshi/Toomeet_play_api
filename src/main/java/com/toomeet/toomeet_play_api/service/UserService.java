package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.response.UserAuthenticationResponse;
import com.toomeet.toomeet_play_api.entity.User;

public interface UserService {
    User getUserByEmail(String email);

    boolean existedByEmail(String email);

    User saveUser(User user);

    User getUserByUserId(String userId);

    UserAuthenticationResponse getUserAuthentication(User user);
}
