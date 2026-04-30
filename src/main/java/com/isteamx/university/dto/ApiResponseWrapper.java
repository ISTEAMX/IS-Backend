package com.isteamx.university.dto;

import org.springframework.http.HttpStatus;

public record ApiResponseWrapper<T>(T data, String message, int status) {

    public static <T> ApiResponseWrapper<T> success(T data, String message) {
        return new ApiResponseWrapper<>(data, message, HttpStatus.OK.value());
    }

    public static <T> ApiResponseWrapper<T> created(T data, String message) {
        return new ApiResponseWrapper<>(data, message, HttpStatus.CREATED.value());
    }

    public static ApiResponseWrapper<Void> success(String message) {
        return new ApiResponseWrapper<>(null, message, HttpStatus.OK.value());
    }

    public static ApiResponseWrapper<String> error(String message, HttpStatus status) {
        return new ApiResponseWrapper<>(null, message, status.value());
    }
}

