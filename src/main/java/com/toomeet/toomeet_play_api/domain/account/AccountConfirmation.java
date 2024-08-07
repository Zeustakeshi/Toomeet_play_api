package com.toomeet.toomeet_play_api.domain.account;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
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

    private String email;
    private String password;
    private String name;
}
