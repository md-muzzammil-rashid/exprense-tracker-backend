package com.mdmuzzammilrashid.expensetracker.services;

import java.util.List;

import com.mdmuzzammilrashid.expensetracker.Entity.WalletEntity;
import com.mdmuzzammilrashid.expensetracker.models.WalletModel;

public interface IWalletServices {
    public WalletEntity addWallet(WalletModel wallet);
    public WalletEntity incrementWalletAmount(String walletId, Double amount);
    public WalletEntity decrementWalletAmount(String walletId, Double amount);
    public WalletEntity getWalletById(String userId, String walletId);
    public Boolean deleteWallet(String walletId);
    public List<WalletEntity> getWalletByUserId(String userId);
    public Boolean verifyUserWithWallet(String userId, String walletId);
}
