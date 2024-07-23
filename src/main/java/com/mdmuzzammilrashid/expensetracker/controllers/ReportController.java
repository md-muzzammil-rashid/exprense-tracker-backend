package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;
import com.mdmuzzammilrashid.expensetracker.enums.IncomeCategory;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionType;
import com.mdmuzzammilrashid.expensetracker.services.IReportServices;
import com.mdmuzzammilrashid.expensetracker.services.ITransactionService;
import com.mdmuzzammilrashid.expensetracker.utils.ApiResponse;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    IReportServices reportServices;
    
    @GetMapping("/transactions-by-date")
    public ResponseEntity<?> getTransactionByDate(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String walletId) {
        if(walletId == null || walletId.trim().isEmpty()){
            List<TransactionEntity> transactions = reportServices.getTransactionBetweenDates(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);

        }else{
            List<TransactionEntity> transactions = reportServices.getTransactionByWalletIdBetweenDates(walletId,startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);
        }
    }
    
    @GetMapping("/category/expense")
    public ResponseEntity<?> getExpenseByCategory(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String walletId) {
        if(walletId == null || walletId.trim().isEmpty()){
            Map<ExpenseCategory, List<TransactionEntity>> transactions = reportServices.getExpenseByCategory(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Report fetched Successfully", transactions), HttpStatus.OK);
        }
        Map<ExpenseCategory, List<TransactionEntity>> transactions = reportServices.getExpenseByCategoryAndWalletId(walletId, startDate, endDate);
        return new ResponseEntity<>(new ApiResponse<>(200, "Report fetched Successfully", transactions), HttpStatus.OK);
    }

    @GetMapping("/category/income")
    public ResponseEntity<?> getIncomeByCategory(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String walletId) {
        if(walletId == null || walletId.trim().isEmpty()){
            Map<IncomeCategory, List<TransactionEntity>> transactions = reportServices.getIncomeByCategory(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Report fetched Successfully", transactions), HttpStatus.OK);
        }
        Map<IncomeCategory, List<TransactionEntity>> transactions = reportServices.getIncomeByCategoryAndWalletId(walletId, startDate, endDate);
        return new ResponseEntity<>(new ApiResponse<>(200, "Report fetched Successfully", transactions), HttpStatus.OK);
    }

    @GetMapping("/top-expense")
    public ResponseEntity getTopExpense(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String limit) {
        if(limit==null)limit = "5";
        List<TransactionEntity> transactions = reportServices.getTopExpense(Integer.parseInt(limit), startDate, endDate);
        return new ResponseEntity<>(new ApiResponse<>(200, "fetched Successfull", transactions), HttpStatus.OK) ;
    }

    @GetMapping("/top-income")
    public ResponseEntity getTopIncome(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String limit) {
        if(limit==null)limit = "5";
        List<TransactionEntity> transactions = reportServices.getTopIncome(Integer.parseInt(limit), startDate, endDate);
        return new ResponseEntity<>(new ApiResponse<>(200, "fetched Successfull", transactions), HttpStatus.OK) ;
    }

    @GetMapping("/monthly-saving")
    public ResponseEntity getMonthlySaving(@RequestParam String month, @RequestParam String year) {
        Double savings = reportServices.getNetSavingByMonth(month, year);
        return new ResponseEntity<>(new ApiResponse<>(200, "fetched Successfull", savings), HttpStatus.OK);
    }
    
    @GetMapping("/yearly-saving")
    public ResponseEntity getYearlySaving( @RequestParam String year) {
        Double savings = reportServices.getNetSavingByYear(Integer.parseInt(year));
        return new ResponseEntity<>(new ApiResponse<>(200, "fetched Successfull", savings), HttpStatus.OK);
    }
    
    
    
}
