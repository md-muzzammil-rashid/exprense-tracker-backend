package com.mdmuzzammilrashid.expensetracker.repositories;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mdmuzzammilrashid.expensetracker.Entity.UserEntity;

@Repository
public interface IUserRepo extends JpaRepository<UserEntity, UUID> {
    
    @Query("FROM UserEntity WHERE username=:username OR email=:email")
    public List<UserEntity> findUserByUsernameOrEmail(String username, String email);

}
