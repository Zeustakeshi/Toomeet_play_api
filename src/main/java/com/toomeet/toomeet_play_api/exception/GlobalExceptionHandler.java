package com.toomeet.toomeet_play_api.exception;


import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorCode errorCode = ErrorCode.REQUEST_VALIDATION_FAILED;
        return new ResponseEntity<>(ApiResponse.error(errorCode.getCode(), errors), ex.getStatusCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ApiResponse<?> response = ApiResponse.error(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AuthenticationException ex) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_ERROR;
        ApiResponse<?> response = ApiResponse.error(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException ex) {
        return errorCodeToResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ErrorCode errorCode = ErrorCode.METHOD_NOT_SUPPORTED_EXCEPTION;
        ApiResponse<?> response = ApiResponse.error(errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED;
        ex.printStackTrace();
        return errorCodeToResponseEntity(errorCode);
    }


    private ResponseEntity<ApiResponse<?>> errorCodeToResponseEntity(ErrorCode errorCode) {
        ApiResponse<?> response = ApiResponse.error(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

}
