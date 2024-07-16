package com.mdmuzzammilrashid.expensetracker.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.*;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.models.TransactionModel;
import com.mdmuzzammilrashid.expensetracker.repositories.ITransactionRepo;

@Service
public class TransactionServiceImplementation implements ITransactionService {

    @Autowired
    private ITransactionRepo transactionRepository;


    @Override
    public TransactionEntity getTransactionById(String transactionId) {

        Optional<TransactionEntity> transaction = transactionRepository.findById(transactionId);
        if(transaction.isPresent()){
            return transaction.get();
        }else{
            //TODO: throw exception
        }
        return null;
        
    }

    @Override
    public List<TransactionEntity> getAllTransactionByUserId(String userId) {
            return transactionRepository.findByUserId(userId);
    }
    

    @Override
    public List<TransactionEntity> getAllTransactionByWalletId(String walletId) {
        //TODO:
        try {
            Optional<List<TransactionEntity>> transactions = transactionRepository.findByWalletId(walletId);
            if(transactions.isPresent()){
                return transactions.get();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }


    @Override
    public List<TransactionEntity> addTransactions(List<TransactionModel> transactions) {
        List<TransactionEntity> savedTransactions = new ArrayList<>();
        transactions.forEach(transaction -> {
            TransactionEntity entity = new TransactionEntity();
            entity.setUserId(transaction.getUserId());
            entity.setAmount(transaction.getAmount());
            entity.setDate(transaction.getDate());
            entity.setCategory(transaction.getCategory());
            entity.setWalletId(transaction.getWalletId());
            entity.setDescription(transaction.getDescription());
            entity.setTransactionId(transaction.getTransactionId());
            entity.setType(transaction.getType());
            savedTransactions.add(entity);
        });
        transactionRepository.saveAll(savedTransactions);
        return savedTransactions;
    }

    @Override
    public Boolean updateTransaction(String transactionId, TransactionModel transaction) {
        try {
            Optional<TransactionEntity> oldTransaction = transactionRepository.findById(transactionId);
            if(oldTransaction.isPresent()){
                oldTransaction.get().setAmount(transaction.getAmount());
                oldTransaction.get().setCategory(transaction.getCategory());
                oldTransaction.get().setDate(transaction.getDate());
                oldTransaction.get().setDescription(transaction.getDescription());
                oldTransaction.get().setWalletId(transaction.getWalletId());
                oldTransaction.get().setType(transaction.getType());
                transactionRepository.save(oldTransaction.get());
                return true;
            }            
        } catch (Exception e) {
            // TODO: handle exception

        }
        return null;
    }

    @Override
    public Boolean deleteTransaction(String transactionId) {
        try {
            TransactionEntity transaction = getTransactionById(transactionId);
            if(transaction != null){
                transactionRepository.deleteById(transactionId);
                Optional<TransactionEntity> deletedTransaction = transactionRepository.findById(transactionId);
                return !deletedTransaction.isPresent();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
        return false;
    }

    @Override
    public List<TransactionEntity> getAllTransactionBetweenDateRange(String startDate, String endDate) {
        try {
            Optional<List<TransactionEntity>> transactions = transactionRepository.findTransactionBetweenDates(startDate, endDate);
            if(transactions.isPresent()) {
                return transactions.get();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public List<TransactionEntity> getAllTransactionByWalletIdBetweenDateRange(String walletId, String startDate,
            String endDate) {
                try {
                    Optional<List<TransactionEntity>> transactions = transactionRepository.findTransactionByWalletIdBetweenDates(walletId, startDate, endDate);
                    if(transactions.isPresent()) {
                        return transactions.get();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return null;
    }
}
