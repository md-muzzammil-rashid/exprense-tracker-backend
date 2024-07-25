package com.mdmuzzammilrashid.expensetracker.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mdmuzzammilrashid.expensetracker.DTO.UserDetailsResponse;
import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.models.UserModel;

public interface IUserService  {
    public UserEntity registerUser(UserModel user);
    public Boolean isUserPresentWithSameUsernameOrEmail(String username,String email);
    public boolean verifyOTP(String username, Integer otp);
    public UserEntity getUserDetails(String username);
    public void setRefreshToken(String username, String refreshToken);
    public UserDetailsResponse getUserDetailsById(String userId);
    public Boolean setLogOut(String userId);
    public Boolean updateBudget(String userId, String budgetJSON);
}
