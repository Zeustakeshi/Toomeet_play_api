package com.toomeet.toomeet_play_api.dto.response.general;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toomeet.toomeet_play_api.domain.context.RequestContext;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    public static final int DEFAULT_SUCCESS_CODE = 1000;

    private int code;
    private T data;
    private Object errors;
    private boolean isSuccess;
    private LocalDateTime timestamp;
    private String path;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(DEFAULT_SUCCESS_CODE)
                .data(data)
                .isSuccess(true)
                .timestamp(LocalDateTime.now())
                .path(RequestContext.getRequest().getRequestURI())
                .build();
    }

    public static <T> ApiResponse<T> error(int code, Object errors) {
        return ApiResponse.<T>builder()
                .code(code)
                .errors(errors)
                .isSuccess(false)
                .timestamp(LocalDateTime.now())
                .path(RequestContext.getRequest().getRequestURI())
                .build();
    }
}
