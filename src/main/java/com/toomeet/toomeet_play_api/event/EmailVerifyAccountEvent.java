package com.toomeet.toomeet_play_api.event;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class EmailVerifyAccountEvent {
    private String email;
    private String code;
    private String name;
}