package com.mdmuzzammilrashid.expensetracker.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.models.UserModel;

public interface IUserService  {
    public UserEntity registerUser(UserModel user);
    public Boolean isUserPresentWithSameUsernameOrEmail(String username,String email);
    public boolean verifyOTP(String username, Integer otp);
    public UserEntity loginUser(String usernameOrEmail, String password);
    public Object generateAccessAndRefreshToken (String username);
    public UserEntity getUserDetails(String username);
}
