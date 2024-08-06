package com.toomeet.toomeet_play_api.repository.user;

import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByEmail(String email);

    boolean existsByEmail(String email);
}
