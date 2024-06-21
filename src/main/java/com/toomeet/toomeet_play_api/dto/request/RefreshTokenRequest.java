package com.toomeet.toomeet_play_api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotEmpty(message = "Refresh token mus be not empty or null")
    private String token;
}
