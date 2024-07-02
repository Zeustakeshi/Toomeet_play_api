package com.toomeet.toomeet_play_api.domain.account;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.toomeet.toomeet_play_api.entity.Account;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfirmation {
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private String code = NanoIdUtils.randomNanoId();
    private Account account;
}
