package com.toomeet.toomeet_play_api.service.user;

import com.toomeet.toomeet_play_api.dto.request.user.AddVideoHistoryRequest;
import com.toomeet.toomeet_play_api.entity.Account;

public interface UserService {
    String addVideoHistory(AddVideoHistoryRequest request, Account account);
}
