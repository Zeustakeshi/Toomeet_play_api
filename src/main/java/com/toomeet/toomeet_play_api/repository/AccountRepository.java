package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserUserId(String userId);

    Account findByEmail(String email);

    Account findByAccountId(String accountId);

    boolean existsByEmail(String email);
}
