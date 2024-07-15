package com.mdmuzzammilrashid.expensetracker.services;
import java.util.*;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;
import com.mdmuzzammilrashid.expensetracker.enums.IncomeCategory;

public interface IReportServices {
    public List<TransactionEntity> getTransactionBetweenDates(String startDate, String endDate);
    public List<TransactionEntity> getTransactionByWalletIdBetweenDates(String walletId, String startDate, String endDate);
    public List<TransactionEntity> getExpenseByCategory(ExpenseCategory category, String startDate, String endDate);
    public List<TransactionEntity> getIncomeByCategory(IncomeCategory category, String startDate, String endDate);
    public List<TransactionEntity> getTopExpense(Integer limit, String startDate, String endDate); //TODO: make parse date to get month number 
    public List<TransactionEntity> getTopIncome(Integer limit, String startDate, String endDate);
    public Double getNetSavingByMonth(Integer month, Integer Year);
    public Double getNetSavingByYear(Integer year);
    public void getMonthlyIncome(Integer limit);
    public void getMonthlyExpense(Integer limit); // Income group by month {total: Number; transactions:[transaction]} res in jan = 32, feb = 32, mar = 32


}
