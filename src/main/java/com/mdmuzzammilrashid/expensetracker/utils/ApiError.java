package com.mdmuzzammilrashid.expensetracker.utils;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private String message = "Something went wrong";
    private HttpStatus status;
    private Integer statusCode;
    private Boolean success = false;
    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.statusCode = status.value();
        this.status = status;
    }
}
