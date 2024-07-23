package com.mdmuzzammilrashid.expensetracker.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mdmuzzammilrashid.expensetracker.Entity.WalletEntity;

@Repository
public interface  IWalletRepo extends JpaRepository<WalletEntity, String > {
    public Optional<List<WalletEntity>> findAllByUserId(String userId);

    @Query("FROM WalletEntity WHERE userId =:userId AND walletId =:walletId")
    public Optional<WalletEntity> findByUserIdAndWalletId(String userId, String walletId);


}
