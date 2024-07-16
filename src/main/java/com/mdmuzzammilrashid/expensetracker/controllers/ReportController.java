package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.enums.ExpenseCategory;
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
        System.out.println("****************************");
        System.out.println(walletId);
        System.out.println("****************************");
        if(walletId == null || walletId.trim().isEmpty()){
            System.out.println("no wallet id specified");
            Map<ExpenseCategory, List<TransactionEntity>> transactions = reportServices.getExpenseByCategory(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Report fetched Successfully", transactions), HttpStatus.OK);
        }
        Map<ExpenseCategory, List<TransactionEntity>> transactions = reportServices.getExpenseByCategoryAndWalletId(walletId, startDate, endDate);
        return new ResponseEntity<>(new ApiResponse<>(200, "Report fetched Successfully", transactions), HttpStatus.OK);
    }
    
}
