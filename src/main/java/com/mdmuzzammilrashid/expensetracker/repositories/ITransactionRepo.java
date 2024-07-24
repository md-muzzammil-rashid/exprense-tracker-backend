package com.mdmuzzammilrashid.expensetracker.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionType;

@Repository
public interface ITransactionRepo extends JpaRepository<TransactionEntity, String> {
    public Page<TransactionEntity> findByUserId(String userId, Pageable page);
    
    public Optional<TransactionEntity> findByUserIdAndTransactionId( String userId, String transactionId);

    public Optional<List<TransactionEntity>> findByWalletId(String walletId);

    @Query("FROM TransactionEntity WHERE date BETWEEN :startDate AND :endDate AND userId =:userId")
    public Optional<List<TransactionEntity>> findTransactionBetweenDates(String startDate, String endDate, String userId);

    @Query("FROM TransactionEntity WHERE walletId = :walletId AND date BETWEEN :startDate AND :endDate")
    public Optional<List<TransactionEntity>> findTransactionByWalletIdBetweenDates(String walletId, String startDate, String endDate);

    @Query("FROM TransactionEntity WHERE date BETWEEN :startDate AND  :endDate  AND type = :type AND category = :category ")
    public List<TransactionEntity> findTransactionByCategory(String category, String startDate, String endDate, TransactionType type);

    @Query("FROM TransactionEntity WHERE date BETWEEN :startDate AND  :endDate  AND type = :type AND walletId=:walletId AND category = :category")
    public List<TransactionEntity> findTransactionByCategoryAndWalletId(String walletId, String category, String startDate, String endDate, TransactionType type);

    @Query("FROM TransactionEntity WHERE date BETWEEN :startDate AND :endDate AND type = :type ORDER BY amount DESC")
    public List<TransactionEntity> findTopTransaction(TransactionType type, String startDate, String endDate,  Pageable page);
    
    @Query("SELECT SUM(t.amount) FROM TransactionEntity t WHERE t.date BETWEEN :startDate AND :endDate AND t.type = :type")
    public Double findNetTransactionByType(TransactionType type, String startDate, String endDate);

    @Query("FROM TransactionEntity WHERE walletId =:walletId AND userId=:userId")
    public Page<TransactionEntity> findTransactionByWalletId(String walletId, String userId,  Pageable page);
    
}
