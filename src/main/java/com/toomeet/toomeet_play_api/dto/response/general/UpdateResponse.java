/*
 *  UpdateChannelResponse
 *  @author: Minhhieuano
 *  @created 8/7/2024 12:44 PM
 * */

package com.toomeet.toomeet_play_api.dto.response.general;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateResponse<T> {
    private UpdateStatus status;
    private T updatedValue;
    private String message;

    public static <T> UpdateResponse<T> success(T updatedValue) {
        return UpdateResponse.<T>builder()
                .status(UpdateStatus.DONE)
                .updatedValue(updatedValue)
                .build();
    }

    public static UpdateResponse<String> pending(String message) {
        return UpdateResponse.<String>builder()
                .status(UpdateStatus.PENDING)
                .message(message)
                .build();
    }

    public static UpdateResponse<String> info(String message) {
        return UpdateResponse.<String>builder()
                .status(UpdateStatus.DONE)
                .message(message)
                .build();
    }

    private enum UpdateStatus {
        DONE,
        PENDING
    }
}
