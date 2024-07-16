package com.mdmuzzammilrashid.expensetracker.enums;

public enum IncomeCategory implements TransactionCategory{
    SALARY,
    INVESTMENTS,
    BONUS;

    @Override
    public String getCategoryName() {
        return name();
    }
}
