package com.toomeet.toomeet_play_api.service.impl;

import com.toomeet.toomeet_play_api.dto.response.AccountResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.Authority;
import com.toomeet.toomeet_play_api.mapper.AccountMapper;
import com.toomeet.toomeet_play_api.repository.AccountRepository;
import com.toomeet.toomeet_play_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Account getAccountByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public AccountResponse getAccountInfo(Account account) {
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public Account saveNewAccount(Account account) {

        User user = User.builder()
                .account(account)
                .build();

        account.addAllAuthority(Set.of(Authority.NORMAL_USER));
        account.setVerified(true);


        account.setUserId(user.getUserId());
        user.setAccountId(account.getAccountId());

        account.setUser(user);

        return accountRepository.save(account);
    }

    @Override
    public boolean existedChannel(String accountId) {
        return accountRepository.existedChannel(accountId);
    }
}
