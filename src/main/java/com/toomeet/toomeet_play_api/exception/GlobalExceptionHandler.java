package com.toomeet.toomeet_play_api.exception;


import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorCode errorCode = ErrorCode.REQUEST_VALIDATION_FAILED;
        return new ResponseEntity<>(ApiResponse.error(errorCode.getCode(), request, errors), ex.getStatusCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ApiResponse<?> response = ApiResponse.error(errorCode.getCode(), request, ex.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AuthenticationException ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_ERROR;
        ApiResponse<?> response = ApiResponse.error(errorCode.getCode(), request, ex.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException ex, HttpServletRequest request) {
        return errorCodeToResponseEntity(ex.getErrorCode(), request);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED;
        ex.printStackTrace();
        return errorCodeToResponseEntity(errorCode, request);
    }


    private ResponseEntity<ApiResponse<?>> errorCodeToResponseEntity(ErrorCode errorCode, HttpServletRequest request) {
        ApiResponse<?> response = ApiResponse.error(errorCode.getCode(), request, errorCode.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

}
