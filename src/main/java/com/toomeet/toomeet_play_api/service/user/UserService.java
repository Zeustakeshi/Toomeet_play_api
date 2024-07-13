package com.toomeet.toomeet_play_api.service.user;

import com.toomeet.toomeet_play_api.dto.request.user.AddVideoHistoryRequest;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.User;

public interface UserService {
    User getUserById(String userId);

    String addVideoHistory(AddVideoHistoryRequest request, Account account);
}
