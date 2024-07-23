package com.mdmuzzammilrashid.expensetracker.models;

import lombok.Data;


@Data
public class WalletModel {
    private String walletName;
    private Double balance;
    private String code;
    private String userId;

}
