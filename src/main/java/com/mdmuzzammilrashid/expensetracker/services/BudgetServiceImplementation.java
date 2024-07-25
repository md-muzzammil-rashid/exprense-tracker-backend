package com.mdmuzzammilrashid.expensetracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;
import com.mdmuzzammilrashid.expensetracker.repositories.IUserRepo;

public class BudgetServiceImplementation implements IBudgetService {

    @Autowired
    IUserRepo userRepo;
    @Override
    public Boolean updateBudget(String userId, String budget) {
        Optional<UserEntity> user = userRepo.findByUserId(userId);
        if(user.isPresent()){
            user.get().setBudget(budget);
            userRepo.save(user.get());
            return true;
        }
        return false;

    }
    
}
