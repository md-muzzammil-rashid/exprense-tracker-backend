package com.mdmuzzammilrashid.expensetracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.exceptions.AuthException;
import com.mdmuzzammilrashid.expensetracker.repositories.IUserRepo;

@Service
public class UserDetailsServiceImplementation implements  UserDetailsService {

    @Autowired
    IUserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByUsername(username);
        if(!user.isPresent()){
            throw new AuthException("User not found ", HttpStatus.NOT_FOUND);
        }

        return User.builder()
            .username(user.get().getUsername())
            .password(user.get().getPassword())
            .build();
    }
    
}
