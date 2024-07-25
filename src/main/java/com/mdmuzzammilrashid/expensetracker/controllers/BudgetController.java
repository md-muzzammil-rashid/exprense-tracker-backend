package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mdmuzzammilrashid.expensetracker.repositories.IUserRepo;
import com.mdmuzzammilrashid.expensetracker.services.IUserService;
import com.mdmuzzammilrashid.expensetracker.utils.ApiResponse;


@RestController
@RequestMapping("api/v1/budget")
public class BudgetController {

    @Autowired
    IUserService userService;

    @PostMapping("/")
    public ResponseEntity<?> changeBudget(@RequestAttribute String userId, @RequestBody String budgetJSON) {
        //TODO: process POST request
        userService.updateBudget(userId, budgetJSON);
        return new ResponseEntity<>(new ApiResponse<>(200, "Budget changed", null), HttpStatus.OK);
    }
    

    
}
