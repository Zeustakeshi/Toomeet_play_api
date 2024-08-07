/*
 *  VerifyAccountRequest
 *  @author: Minhhieuano
 *  @created 8/7/2024 11:24 AM
 * */

package com.toomeet.toomeet_play_api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyAccountRequest {
    @NotBlank
    @NotNull
    private String code;
}
