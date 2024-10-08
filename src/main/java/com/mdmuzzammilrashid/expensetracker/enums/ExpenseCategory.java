package com.mdmuzzammilrashid.expensetracker.enums;

public enum ExpenseCategory implements TransactionCategory {
    HOUSING ,
    UTILITIES,
    FOOD,
    TRANSPORTATION,
    HEALTHCARE,
    INSURANCE,
    ENTERTAINMENT,
    RECREATION,
    TRAVEL,
    SHOPPING,
    DEBT_PAYMENT,
    SAVING_AND_INVESTMENT,
    FAMILY,
    GIFT_AND_DECORATION,
    PERSONAL_CARE,
    MISCELLANEOUS;

    @Override
    public String getCategoryName() {
        return name();
    }
}
