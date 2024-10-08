package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.Entity.WalletEntity;
import com.mdmuzzammilrashid.expensetracker.models.WalletModel;
import com.mdmuzzammilrashid.expensetracker.services.IWalletServices;
import com.mdmuzzammilrashid.expensetracker.utils.ApiResponse;




@RestController
@RequestMapping("api/v1/wallets")
public class WalletController {

    @Autowired
    IWalletServices walletService;

    @PostMapping("/add-wallet")
    ResponseEntity<?>createWallet(@RequestAttribute()String userId, @RequestBody WalletModel wallet){
        wallet.setUserId(userId);
        WalletEntity newWallet = walletService.addWallet(wallet);
        return new ResponseEntity<>(new ApiResponse<>(201, "New Wallet created", newWallet), HttpStatus.CREATED);
    }
    
    @GetMapping("/{walletId}")
    public ResponseEntity<?> getWalletById(@RequestAttribute()String userId, @PathVariable String walletId) {
        //validate user
        System.out.println(walletId);
        WalletEntity wallet = walletService.getWalletById(userId, walletId);
        if(wallet != null){
            return new ResponseEntity<>(new ApiResponse<>(200,"Fetched Successful", wallet), HttpStatus.OK);
        }

        return null;
    }

    @GetMapping("/get-wallets")
    public ResponseEntity<?> getWalletsByUserId(@RequestAttribute() String userId) {
        //get user from header
        return new ResponseEntity(new ApiResponse<>(200, "fetched", walletService.getWalletByUserId(userId)), HttpStatus.OK);
    }
    
    
}
