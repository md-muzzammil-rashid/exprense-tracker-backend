package com.mdmuzzammilrashid.expensetracker.services;

import java.util.List;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.models.TransactionModel;

public interface ITransactionService {
    public List<TransactionEntity> addTransactions(List<TransactionModel> transactions, String userId);
    public TransactionEntity getTransactionById(String transactionId, String userId);
    public Boolean updateTransaction(String transactionId, TransactionModel transaction, String userId);
    public Boolean deleteTransaction(String transactionId, String userId);
    public List<TransactionEntity> getAllTransactionByUserId(String userId);
    public List<TransactionEntity> getAllTransactionByWalletId(String walletId, String userId, Integer limit);
    public List<TransactionEntity> getAllTransactionBetweenDateRange(String startDate, String endDate);
    public List<TransactionEntity> getAllTransactionByWalletIdBetweenDateRange(String walletId, String startDate, String endDate, String userId);
    public List<TransactionEntity> addIncome(List<TransactionEntity>transactions);

}
