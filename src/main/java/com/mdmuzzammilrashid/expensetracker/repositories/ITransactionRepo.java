package com.mdmuzzammilrashid.expensetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;

@Repository
public interface ITransactionRepo extends JpaRepository<TransactionEntity, String> {
    public List<TransactionEntity> findByUserId(String userId);
    
    public Optional<TransactionEntity> findByUserIdAndTransactionId( String userId, String transactionId);

    public Optional<List<TransactionEntity>> findByWalletId(String walletId);

    @Query("FROM TransactionEntity WHERE date BETWEEN :startDate AND :endDate")
    public Optional<List<TransactionEntity>> findTransactionBetweenDates(String startDate, String endDate);

    @Query("FROM TransactionEntity WHERE walletId = :walletId AND date BETWEEN :startDate AND :endDate")
    public Optional<List<TransactionEntity>> findTransactionByWalletIdBetweenDates(String walletId, String startDate, String endDate);
}
