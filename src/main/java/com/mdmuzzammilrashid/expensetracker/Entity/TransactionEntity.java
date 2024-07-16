package com.mdmuzzammilrashid.expensetracker.Entity;

import java.util.UUID;
import java.util.ArrayList;
import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionCategory;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class TransactionEntity {
    @Id
    private String transactionId;
    private String walletId;
    private String description;
    private Double amount;
    private String date;

    private String category;

    private String userId;
    private ArrayList<String> tags;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
