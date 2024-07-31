package com.toomeet.toomeet_play_api.service.user.impl;

import com.toomeet.toomeet_play_api.dto.response.account.AccountResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.enums.Role;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.AccountMapper;
import com.toomeet.toomeet_play_api.repository.user.AccountRepository;
import com.toomeet.toomeet_play_api.service.user.AccountService;
import com.toomeet.toomeet_play_api.service.util.NanoIdService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final NanoIdService nanoIdService;

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
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
    @Transactional
    public Account saveNewAccount(Account account) {

        User user = User.builder()
                .account(account)
                .build();

        Channel channel = Channel.builder()
                .account(account)
                .avatar(account.getImage())
                .name("channel-" + nanoIdService.generateCustomNanoId(5))
                .build();

        account.addAllAuthority(Set.of(Role.NORMAL_USER));
        account.setVerified(true);
        account.setUser(user);
        account.setChannel(channel);

        return accountRepository.save(account);
    }

}
