package com.mdmuzzammilrashid.expensetracker.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdmuzzammilrashid.expensetracker.DTO.UserDetailsResponse;
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
        user.setUsername(userData.getUsername().trim().toLowerCase());
        user.setEmail(userData.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        Integer OTP = rand.nextInt(100000, 999999);
        user.setVerificationOTP(OTP.toString());
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
    public UserEntity getUserDetails(String username) {
        // return userRepo.findById(userId).get();
        return userRepo.findByUsername(username).get();
    }

    @Override
    public void setRefreshToken(String username, String refreshToken) {
        UserEntity user = userRepo.findByUsername(username).get();
        user.setRefreshToken(refreshToken);
        userRepo.save(user);
    }

    @Override
    public UserDetailsResponse getUserDetailsById(String userId) {
         UserEntity user = userRepo.findByUserId(userId).get();
         System.out.println(user);
         UserDetailsResponse userDetail = new UserDetailsResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getDisplayName(), user.getVerified(), user.getAvatar(), user.getBudget());
        // UserDetailsResponse userDetail = new UserDetailsResponse();
        // userDetail.setUserId(user.getUserId());

        return userDetail;
    }

    @Override
    public Boolean setLogOut(String userId) {
        try {
            Optional<UserEntity> user = userRepo.findByUserId(userId);
            if(user.isPresent()){

                user.get().setRefreshToken(null);
                userRepo.save(user.get());
                return true;
            }
        } catch (Exception e) {
            //TODO: throw exception
        }
        return false;
    }

    @Override
    public Boolean updateBudget(String userId, String budgetJSON) {
        try {
            Optional<UserEntity> user = userRepo.findByUserId(userId);
            if(user.isPresent()){

                user.get().setBudget(budgetJSON);
                userRepo.save(user.get());
                return true;
            }
        } catch (Exception e) {
            //TODO: throw exception
        }
        return false;    
    }

    
}
