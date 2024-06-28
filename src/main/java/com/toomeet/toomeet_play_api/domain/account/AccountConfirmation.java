package com.toomeet.toomeet_play_api.domain.account;

import com.toomeet.toomeet_play_api.entity.Account;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfirmation {
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private String code = UUID.randomUUID().toString();
    private Account account;
}
