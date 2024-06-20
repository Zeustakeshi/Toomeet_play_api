package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.entity.User;

public interface UserService {
    User getUserByEmail(String email);

    boolean existedByEmail(String email);

    User saveUser(User user);

}
