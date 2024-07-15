package com.mdmuzzammilrashid.expensetracker.exceptions;

import org.springframework.http.HttpStatus;

import com.mdmuzzammilrashid.expensetracker.enums.RegistrationExceptionType;

import lombok.Getter;

public class AuthException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private Integer statusCode;
    public AuthException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
        this.statusCode = status.value();
    }
    public String getMessage() {
        return message;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public Integer getStatusCode() {
        return statusCode;
    }
    
}
