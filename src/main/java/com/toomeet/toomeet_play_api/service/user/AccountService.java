package com.toomeet.toomeet_play_api.service.user;

import com.toomeet.toomeet_play_api.dto.response.account.AccountResponse;
import com.toomeet.toomeet_play_api.entity.Account;

public interface AccountService {
    boolean existsByEmail(String email);

    Account getAccountById(String accountId);

    Account getAccountByEmail(String email);

    Account updateAccount(Account account);

    AccountResponse getAccountInfo(Account account);

    Account saveNewAccount(Account account);

}
