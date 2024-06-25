package com.toomeet.toomeet_play_api.config.auditing;

import com.toomeet.toomeet_play_api.entity.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Account> {

    @Override
    public Optional<Account> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) return Optional.empty();
        Account account = (Account) authentication.getPrincipal();

        return Optional.ofNullable(account);

    }
}
