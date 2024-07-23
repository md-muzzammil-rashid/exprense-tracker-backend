package com.mdmuzzammilrashid.expensetracker.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;
import com.mdmuzzammilrashid.expensetracker.enums.IncomeCategory;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionType;
import com.mdmuzzammilrashid.expensetracker.repositories.ITransactionRepo;


@Service
public class ReportServicesImplementation implements IReportServices {

    @Autowired
    private ITransactionRepo transactionRepository;

    private TransactionType transactionType;

    @Override
    public List<TransactionEntity> getTransactionBetweenDates(String startDate, String endDate) {
        try {
            Optional<List<TransactionEntity>> transactions = transactionRepository.findTransactionBetweenDates(startDate, endDate);
            if(transactions.isPresent()) {
                return transactions.get();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public List<TransactionEntity> getTransactionByWalletIdBetweenDates(String walletId, String startDate,
            String endDate) {
        try {
            Optional<List<TransactionEntity>> transactions = transactionRepository
                    .findTransactionByWalletIdBetweenDates(walletId, startDate, endDate);
            if (transactions.isPresent()) {
                return transactions.get();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }

    @Override
    public Map<ExpenseCategory, List<TransactionEntity>> getExpenseByCategory(String startDate, String endDate) {
        Map<ExpenseCategory, List<TransactionEntity>> transactions = new HashMap<>();

        for(ExpenseCategory category : ExpenseCategory.values()){
            List<TransactionEntity> transaction = transactionRepository.findTransactionByCategory(category.getCategoryName(), startDate, endDate, transactionType.EXPENSE);
            transactions.computeIfAbsent(category, k -> new ArrayList<>()).addAll(transaction);
        }
        return transactions;
        
        //TODO:handle exception

    }

    @Override
    public Map<IncomeCategory, List<TransactionEntity>> getIncomeByCategory( String startDate, String endDate) {
        Map<IncomeCategory, List<TransactionEntity>> transactions = new HashMap<>();

        for(IncomeCategory category : IncomeCategory.values()){
            List<TransactionEntity> transaction = transactionRepository.findTransactionByCategory(category.getCategoryName(), startDate, endDate, transactionType.INCOME);
            transactions.computeIfAbsent(category, k -> new ArrayList<>()).addAll(transaction);
        }
        return transactions;
        //TODO: handle exception

    }

    @Override
    public Map<ExpenseCategory, List<TransactionEntity>> getExpenseByCategoryAndWalletId(String walletId,
            String startDate, String endDate) {
                System.out.println("\n the walletid " + walletId );
        Map<ExpenseCategory, List<TransactionEntity>> transactions = new HashMap<>();

        for(ExpenseCategory category : ExpenseCategory.values()){
            List<TransactionEntity> transaction = transactionRepository.findTransactionByCategoryAndWalletId(walletId, category.getCategoryName(), startDate, endDate, TransactionType.EXPENSE);
            transaction.forEach(t->System.out.println(t));
            transactions.computeIfAbsent(category, k -> new ArrayList<>()).addAll(transaction);
        }
        return transactions;
        //TODO: handle exception
    }

    @Override
    public Map<IncomeCategory, List<TransactionEntity>> getIncomeByCategoryAndWalletId(String walletId,
            String startDate, String endDate) {
                System.out.println("\n the walletId is " + walletId);
                Map<IncomeCategory, List<TransactionEntity>> transactions = new HashMap<>();

                for(IncomeCategory category : IncomeCategory.values()){
                    List<TransactionEntity> transaction = transactionRepository.findTransactionByCategoryAndWalletId(walletId, category.getCategoryName(), startDate, endDate, transactionType.INCOME);
                    transactions.computeIfAbsent(category, k -> new ArrayList<>()).addAll(transaction);
                }
                return transactions;
                //TODO: handle exception
    }


    @Override
    public List<TransactionEntity> getTopExpense(Integer limit, String startDate, String endDate) {
        Pageable page = PageRequest.of(0, limit);
        return transactionRepository.findTopTransaction(transactionType.EXPENSE, startDate, endDate, page);
        //TODO: handle exception
    }

    @Override
    public List<TransactionEntity> getTopIncome(Integer limit, String startDate, String endDate) {
        Pageable page = PageRequest.of(0, limit);
        return transactionRepository.findTopTransaction(transactionType.INCOME, startDate, endDate, page);
        // TODO: handle exceptions 
    }

    @Override
    public Double getNetSavingByMonth(String month, String year) {
        //TODO: send invalid date for month >12 and year.length > 4
        if(month.length()<2){
            month = "0"+month;
        }
        String startDate = year+"-"+month+"-01";
        String endDate = year+"-"+month+"-31";
        Double totalIncome  = transactionRepository.findNetTransactionByType(transactionType.INCOME, startDate, endDate);
        Double totalExpense = transactionRepository.findNetTransactionByType(transactionType.EXPENSE, startDate, endDate);
        if(totalExpense != null && totalExpense != null)return totalIncome - totalExpense;
        if(totalIncome != null) return totalIncome;
        return 0.0;

    }

    @Override
    public Double getNetSavingByYear(Integer year) {
        // TODO Auto-generated method stub
        String startDate = year.toString()+"-"+"01-01";
        String endDate = year.toString()+"-"+"12-31";
        Double totalIncome  = transactionRepository.findNetTransactionByType(transactionType.INCOME, startDate, endDate);
        Double totalExpense = transactionRepository.findNetTransactionByType(transactionType.EXPENSE, startDate, endDate);
        if(totalExpense != null && totalExpense != null)return totalIncome - totalExpense;
        if(totalIncome != null) return totalIncome;
        return 0.0;

    }

    @Override
    public List<TransactionEntity> getIncomeGroupByMonth(Integer limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIncomeGroupByMonth'");
    }

    @Override
    public List<TransactionEntity> getExpenseGroupByMonth(Integer limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExpenseGroupByMonth'");
    }

    @Override
    public List<TransactionEntity> getIncomeGroupByYear(Integer limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIncomeGroupByYear'");
    }

    @Override
    public List<TransactionEntity> getExpenseGroupByYear(Integer limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExpenseGroupByYear'");
    }



}
