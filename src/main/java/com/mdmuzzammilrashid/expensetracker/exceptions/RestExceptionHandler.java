package com.mdmuzzammilrashid.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.utils.ApiError;

@RestController
@ControllerAdvice
public class RestExceptionHandler {
    // Handle exceptions here...
    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistException(AuthException exception) {
        ApiError error = new ApiError(exception.getMessage(), exception.getStatus());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
