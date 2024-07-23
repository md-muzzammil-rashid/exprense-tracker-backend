package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.DTO.LoginRequest;
import com.mdmuzzammilrashid.expensetracker.DTO.LoginResponse;
import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.models.UserModel;
import com.mdmuzzammilrashid.expensetracker.services.IJWTServices;
import com.mdmuzzammilrashid.expensetracker.services.IUserService;
import com.mdmuzzammilrashid.expensetracker.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    IJWTServices jwtServices;

    @Autowired
    IUserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/register")
    ResponseEntity <?> registerUser(@RequestBody UserModel user){
        UserEntity registerUser = userService.registerUser(user);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginRequest user) {
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername().trim().toLowerCase(), user.getPassword()));
        if(authentication.isAuthenticated()){
            UserEntity userEntity = userService.getUserDetails(authentication.getName());
            String accessToken  = jwtServices.generateAccessToken(userEntity.getUserId().toString(), userEntity.getUsername());
            String refreshToken  = jwtServices.generateRefreshToken(userEntity.getUserId().toString(), userEntity.getUsername());
            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
            return new ResponseEntity<>(new ApiResponse<>(200, "Login Successful", loginResponse), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("not authenticated", HttpStatus.BAD_REQUEST);
        }
    }

}
