package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.entity.User;

public interface UserService {

    User saveUser(User user);

    User getUserByUserId(String userId);

}
