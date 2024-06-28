package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserUserId(String userId);

    Account findByEmail(String email);

    Account findByAccountId(String accountId);

    boolean existsByEmail(String email);

    @Query("select " +
            "case when a.channel is not null " +
            "then true " +
            "else false end " +
            "from Account a " +
            "where a.accountId = :accountId")
    boolean existedChannel(@Param("accountId") String accountId);
}
