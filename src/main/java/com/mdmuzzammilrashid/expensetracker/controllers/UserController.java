package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.DTO.LoginRequest;
import com.mdmuzzammilrashid.expensetracker.DTO.LoginResponse;
import com.mdmuzzammilrashid.expensetracker.DTO.UserDetailsResponse;
import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.exceptions.AuthException;
import com.mdmuzzammilrashid.expensetracker.models.UserModel;
import com.mdmuzzammilrashid.expensetracker.services.IJWTServices;
import com.mdmuzzammilrashid.expensetracker.services.IUserService;
import com.mdmuzzammilrashid.expensetracker.utils.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    IJWTServices jwtServices;

    @Autowired
    IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/register")
    ResponseEntity <?> registerUser(@RequestBody UserModel user){
        UserEntity registerUser = userService.registerUser(user);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity <?>loginUser(@RequestBody LoginRequest user) {
        if(!passwordEncoder.matches(user.getPassword(), userService.getUserDetails(user.getUsername()).getPassword())){
            throw new AuthException("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }

        // Validate username or email with password
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername().trim().toLowerCase(), user.getPassword()));
        if(authentication.isAuthenticated()){
            UserEntity userEntity = userService.getUserDetails(authentication.getName());
            String accessToken  = jwtServices.generateAccessToken(userEntity.getUserId().toString(), userEntity.getUsername());
            String refreshToken  = jwtServices.generateRefreshToken(userEntity.getUserId().toString(), userEntity.getUsername());
            userService.setRefreshToken(userEntity.getUsername(), refreshToken);
            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
            return new ResponseEntity<>(new ApiResponse<>(200, "Login Successful", loginResponse), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("not authenticated", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user-detail")
    public ResponseEntity<?> getUserDetails(@RequestAttribute String userId) {
        UserDetailsResponse user = userService.getUserDetailsById(userId);
        return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfull", user), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> setLogOut(@RequestAttribute String userId) {
        String message = "Log out Successful ";
        System.out.println(message);
        Boolean logoutSuccess = userService.setLogOut(userId);
        if(!logoutSuccess)message="Failed to Logout";
        return new ResponseEntity<>(new ApiResponse<>(
            logoutSuccess?200:500,
            message,
            null),
            logoutSuccess? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR
            );

    }
    

}
