package com.toomeet.toomeet_play_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Email can not be empty or null")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password can not be empty or null")
    private String password;
}
