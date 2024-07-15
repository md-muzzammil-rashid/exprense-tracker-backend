package com.mdmuzzammilrashid.expensetracker.services;

import org.apache.el.stream.Optional;
import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.models.TransactionModel;

import java.util.List;

public interface ITransactionService {
    public List<TransactionEntity> addTransactions(List<TransactionModel> transactions);
    public TransactionEntity getTransactionById(String transactionId);
    public Boolean updateTransaction(String transactionId, TransactionModel transaction);
    public Boolean deleteTransaction(String transactionId);
    public List<TransactionEntity> getAllTransactionByUserId(String userId);
    public List<TransactionEntity> getAllTransactionByWalletId(String userId);
    public List<TransactionEntity> getAllTransactionBetweenDateRange(String startDate, String endDate);
    public List<TransactionEntity> getAllTransactionByWalletIdBetweenDateRange(String walletId, String startDate, String endDate);

}
