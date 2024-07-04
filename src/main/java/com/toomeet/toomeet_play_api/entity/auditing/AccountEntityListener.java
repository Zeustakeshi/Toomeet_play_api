package com.toomeet.toomeet_play_api.entity.auditing;

import com.toomeet.toomeet_play_api.entity.Account;
import jakarta.persistence.PrePersist;

public class AccountEntityListener {
    @PrePersist
    public void prePersist(Account account) {
        if (account.getChannelId() == null) account.setChannelId(account.getChannel().getId());
        if (account.getUserId() == null) account.setUserId(account.getUser().getId());
    }
}
