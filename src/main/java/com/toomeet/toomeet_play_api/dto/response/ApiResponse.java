package com.toomeet.toomeet_play_api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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


    public static <T> ApiResponse<T> success(HttpServletRequest request, T data) {
        return ApiResponse.<T>builder()
                .code(DEFAULT_SUCCESS_CODE)
                .data(data)
                .isSuccess(true)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    public static <T> ApiResponse<T> error(int code, HttpServletRequest request, Object errors) {
        return ApiResponse.<T>builder()
                .code(code)
                .errors(errors)
                .isSuccess(false)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

}
