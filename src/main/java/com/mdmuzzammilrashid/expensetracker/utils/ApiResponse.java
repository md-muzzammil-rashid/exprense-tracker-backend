package com.mdmuzzammilrashid.expensetracker.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private Integer statusCode;
    private String message;
    private Boolean success;
    private T data;

    public ApiResponse(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.success = true;
    }
    public ApiResponse(Integer statusCode, String message, T data, Boolean success) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.success = success;
    }
}
