package com.mdmuzzammilrashid.expensetracker.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.exceptions.AuthException;
import com.mdmuzzammilrashid.expensetracker.models.UserModel;
import com.mdmuzzammilrashid.expensetracker.repositories.IUserRepo;

@Service
public class UserServiceImplementation implements IUserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Random rand = new Random();
    @Override
    public UserEntity registerUser(UserModel userData) {
        UserEntity user =  new UserEntity();
        if(isContainNullValue(userData)){
            //throw the exception of null value
            throw new AuthException("All fields must be filled", HttpStatus.BAD_REQUEST);
        }
        if(!userData.getPassword().equals( userData.getConfirmPassword()))return null;
        if(Boolean.TRUE.equals(isUserPresentWithSameUsernameOrEmail(userData.getUsername(), userData.getEmail()))){
            //throw the exception of User With Same Username or Email Already Exists
            throw new AuthException("User with same username or email already exists", HttpStatus.CONFLICT);
        }
        user.setDisplayName(userData.getDisplayName());
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        Integer OTP = rand.nextInt(100000, 999999);
        user.setVerificationOTP(OTP);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);
        user.setVerificationOTPExpiry(expiryTime );
        return userRepo.save(user);
    }
    @Override
    public Boolean isUserPresentWithSameUsernameOrEmail(String username, String email) {
        List<UserEntity> existedUsers = userRepo.findUserByUsernameOrEmail(username.trim().toLowerCase(), email.trim().toLowerCase());
        return !existedUsers.isEmpty();
    }
    public Boolean isContainNullValue(UserModel user){
        if(user.getUsername() == null || user.getEmail() == null || user.getPassword() == null || user.getConfirmPassword() == null || user.getDisplayName() == null){
            return true;
        }
        if(user.getUsername().trim().equals("") || user.getEmail().trim().equals("") || user.getPassword().trim().equals("") || user.getConfirmPassword().equals("") || user.getDisplayName().equals("")){
            return true;
        }
        return false;
    }
    @Override
    public boolean verifyOTP(String username, Integer otp) {
        // TODO: Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyOTP'");
    }
    @Override
    public UserEntity loginUser(String usernameOrEmail, String password) {
        UserEntity user = userRepo.findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get(0);
        if(!user){
            throw new AuthException("Invalid username or email", HttpStatus.UNAUTHORIZED);
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new AuthException("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        //TODO: generate access and refresh token 

        return user;
    }
    @Override
    public Object generateAccessAndRefreshToken(String username) {
        //TODO:
        return null;
    }

}
