package com.mdmuzzammilrashid.expensetracker.services;
import java.util.*;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;
import com.mdmuzzammilrashid.expensetracker.enums.IncomeCategory;

public interface IReportServices {
    public List<TransactionEntity> getTransactionBetweenDates(String startDate, String endDate);
    public List<TransactionEntity> getTransactionByWalletIdBetweenDates(String walletId, String startDate, String endDate);
    public Map<ExpenseCategory, List<TransactionEntity>> getExpenseByCategory(String startDate, String endDate);
    public Map<ExpenseCategory, List<TransactionEntity>> getExpenseByCategoryAndWalletId(String walletId, String startDate, String endDate);
    public Map<IncomeCategory, List<TransactionEntity>> getIncomeByCategory(String startDate, String endDate);
    public Map<IncomeCategory, List<TransactionEntity>> getIncomeByCategoryAndWalletId(String walletId, String startDate, String endDate);
    public List<TransactionEntity> getTopExpense(Integer limit, String startDate, String endDate); //TODO: make parse date to get month number 
    public List<TransactionEntity> getTopIncome(Integer limit, String startDate, String endDate);
    public Double getNetSavingByMonth(String month, String Year);
    public Double getNetSavingByYear(Integer year);
    public List<TransactionEntity> getIncomeGroupByMonth(Integer limit);
    public List<TransactionEntity> getExpenseGroupByMonth(Integer limit); // Income group by month {total: Number; transactions:[transaction]} res in jan = 32, feb = 32, mar = 32
    public List<TransactionEntity> getIncomeGroupByYear(Integer limit);
    public List<TransactionEntity> getExpenseGroupByYear(Integer limit); 

}
