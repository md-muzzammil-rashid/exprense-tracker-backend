package com.mdmuzzammilrashid.expensetracker.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String walletId;
    private Double balance;
    private String userId;
    private String walletName;
    private String code;

}
