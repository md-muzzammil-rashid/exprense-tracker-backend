package com.mdmuzzammilrashid.expensetracker.Entity;

import java.util.UUID;
import java.util.ArrayList;
import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TransactionEntity {
    @Id
    private String transactionId;
    private String walletId;
    private String description;
    private Double amount;
    private String date;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;
    private String userId;
    private ArrayList<String> tags;
}
