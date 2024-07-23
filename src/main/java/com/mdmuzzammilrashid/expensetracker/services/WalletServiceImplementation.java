package com.mdmuzzammilrashid.expensetracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdmuzzammilrashid.expensetracker.Entity.WalletEntity;
import com.mdmuzzammilrashid.expensetracker.models.WalletModel;
import com.mdmuzzammilrashid.expensetracker.repositories.IWalletRepo;


@Service
public class WalletServiceImplementation implements IWalletServices {

    @Autowired
    private IWalletRepo walletRepository;

    @Override
    public WalletEntity addWallet(WalletModel wallet) {
        WalletEntity newWallet = new WalletEntity();
        newWallet.setBalance(wallet.getBalance());
        newWallet.setUserId(wallet.getUserId());
        newWallet.setCode(wallet.getCode());
        newWallet.setWalletName(wallet.getWalletName());
        return walletRepository.save(newWallet);
    }

    @Override
    public WalletEntity decrementWalletAmount(String walletId, Double amount) {
        Optional<WalletEntity> wallet = walletRepository.findById(walletId);
        if(wallet.isPresent()){
            Double previousBalance = wallet.get().getBalance();
            if(previousBalance-amount < 0){
                //TODO: throw exception 
                return null;
            }
            wallet.get().setBalance(previousBalance-amount);
            if(wallet.get().getBalance()<0){
                //TODO: throw exception
                return null;
            }
            return walletRepository.save(wallet.get());
        }
        //TODO: if not present throw exception
        return null;
    }
    @Override
    public WalletEntity incrementWalletAmount(String walletId, Double amount) {
        Optional<WalletEntity> wallet = walletRepository.findById(walletId);
        if(wallet.isPresent()){
            Double previousBalance = wallet.get().getBalance();
            wallet.get().setBalance(previousBalance+amount);
            return walletRepository.save(wallet.get());
        }
        //TODO: if not present throw exception
        return null;
    }

    @Override
    public WalletEntity getWalletById(String userId, String walletId) {
        Optional<WalletEntity> wallet = walletRepository.findById(walletId);
        if(wallet.isPresent()) {
            if(wallet.get().getUserId().equals(userId))
            return wallet.get();
            else{
                //TODO: throw exception that user not authorized
            }
        }
        //TODO: if not present throw exception
        return null;
    }

    @Override
    public Boolean deleteWallet(String walletId) {
        Optional<WalletEntity> wallet = walletRepository.findById(walletId);
        if(wallet.isPresent()){
            walletRepository.deleteById(walletId);
            Optional<WalletEntity> deletedWallet = walletRepository.findById(walletId);
            if(deletedWallet.isPresent()){
                //TODO: throw exception that walled not deleted
            }
            return true;
        }else{
            //TODO: if not present throw exception
            return false;
        }
    }

    @Override
    public List<WalletEntity> getWalletByUserId(String userId) {
        return walletRepository.findAllByUserId(userId).get();
    }

    @Override
    public Boolean verifyUserWithWallet(String userId, String walletId) {
        // TODO Auto-generated method stub
        return walletRepository.findByUserIdAndWalletId(userId, walletId).isPresent();
    }
    
}
