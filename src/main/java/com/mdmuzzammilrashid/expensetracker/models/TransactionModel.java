package com.mdmuzzammilrashid.expensetracker.models;

import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionCategory;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionModel {
    private String userId;
    private TransactionType type;
    private Double amount;
    private String date;
    private String category;
    private String description;
    private String attachments;
    private String walletId;
    private String transactionId;
}
