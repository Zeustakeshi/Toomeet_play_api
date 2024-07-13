package com.toomeet.toomeet_play_api.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_ERROR(1001, "Unauthorized", HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS(1002, "Email already exists", HttpStatus.CONFLICT),
    ACCESS_DENIED(1003, "Access denied", HttpStatus.FORBIDDEN),
    REQUEST_VALIDATION_FAILED(1004, "Request validation failed", HttpStatus.BAD_REQUEST),
    INVALID_CONFIRMATION_CODE(1005, "Invalid or expired confirmation code", HttpStatus.FORBIDDEN),
    INVALID_OAUTH_CODE(1006, "Invalid OAuth code", HttpStatus.BAD_REQUEST),
    OAUTH_ACCESS_TOKEN_ERROR(1007, "Failed to retrieve access token from OAuth server", HttpStatus.INTERNAL_SERVER_ERROR),
    OAUTH_LOAD_USER_INFO_ERROR(1008, "Failed to fetch user information from OAuth server", HttpStatus.INTERNAL_SERVER_ERROR),
    GITHUB_OAUTH_EMAIL_EXCEPTION(1009, "Failed to retrieve email information from GitHub", HttpStatus.INTERNAL_SERVER_ERROR),
    SEND_EMAIL_VERIFY_NEW_ACCOUNT_FAIL(1010, "can't send verify email to your account", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIAL(1011, "Invalid username or password", HttpStatus.UNAUTHORIZED),
    CHANNEL_NOT_FOUND(1012, "Channel not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_A_OWNER_CHANNEL(1013, "You are already a channel owner and cannot create a new channel.", HttpStatus.CONFLICT),
    USER_NOT_FOUND(1014, "User not found", HttpStatus.NOT_FOUND),
    PLAYLIST_ALREADY_EXIST(1015, "Playlist already exist.", HttpStatus.CONFLICT),
    INVALID_VIDEO_FORMAT(1016, "Videos in the wrong format. Only .mp4 files are allowed.", HttpStatus.BAD_REQUEST),
    UPLOAD_VIDEO_EXCEPTION(1016, "An error has occurred during the video upload process.", HttpStatus.BAD_REQUEST),
    VIDEO_NOT_FOUND(1017, "Video not found", HttpStatus.NOT_FOUND),
    PLAYLIST_NOT_FOUND(1018, "Playlist not found", HttpStatus.NOT_FOUND),
    METHOD_NOT_SUPPORTED_EXCEPTION(1020, "Method not supported exception", HttpStatus.METHOD_NOT_ALLOWED),
    UPLOAD_IMAGE_EXCEPTION(1021, "Upload image error", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_NAME_ALREADY_EXISTS(1022, "Category name already exists", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(1023, "Category not found", HttpStatus.NOT_FOUND),
    PUBLIC_VIDEO_ERROR(1024, "Something went wrong!. Can't public this video, please check video information again.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_ERROR(1025, "Invalid token", HttpStatus.FORBIDDEN),
    VIDEO_ALREADY_SAVED_IN_HISTORY(1026, "", HttpStatus.CONFLICT),

    ;


    private final String message;
    private final HttpStatus status;
    private final int code;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
