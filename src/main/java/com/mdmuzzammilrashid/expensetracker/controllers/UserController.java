package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.models.UserModel;
import com.mdmuzzammilrashid.expensetracker.services.IUserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    IUserService userService;
    @PostMapping("/register")
    ResponseEntity <?> registerUser(@RequestBody UserModel user){
        UserEntity registerUser = userService.registerUser(user);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }
    
}
