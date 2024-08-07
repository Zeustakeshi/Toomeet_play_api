package com.toomeet.toomeet_play_api.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAccountRequest {

    @NotEmpty(message = "Email can not be empty or null")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password can not be empty or null")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message =
                    "Password must be at least 8 character, one numeric character, one special character, without space and including lowercase, uppercase character")
    private String password;

    @NotEmpty(message = "Name can not be empty or null")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String fullName;
}
