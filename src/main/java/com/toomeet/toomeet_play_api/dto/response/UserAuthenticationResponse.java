package com.toomeet.toomeet_play_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthenticationResponse {
    private String firstName;
    private String lastName;
    private String fullName;
    private String image;
    private String email;
}
