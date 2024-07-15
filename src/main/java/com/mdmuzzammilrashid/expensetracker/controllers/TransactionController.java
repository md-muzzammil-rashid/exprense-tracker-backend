package com.mdmuzzammilrashid.expensetracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.models.TransactionModel;
import com.mdmuzzammilrashid.expensetracker.services.ITransactionService;
import com.mdmuzzammilrashid.expensetracker.utils.ApiResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    
    @Autowired
    ITransactionService transactionService;
    @PostMapping("/add-transactions")
    public ResponseEntity<?> addTransactions(@RequestBody List<TransactionModel> transactions){
        // Add transaction logic here...

        transactionService.addTransactions(transactions);

        ApiResponse< List <TransactionModel> > response = new ApiResponse<>(200, "saved", transactions);
       //TODO: verify user with wallet id
       

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all-transactions")
    public ResponseEntity<?> getMethodName() {
        // String userId = "8f028bb8-0569-42b9-a2c8-d0273cbfa31b";
        String userId = "user1";
        List<TransactionEntity> transactions = transactionService.getAllTransactionByUserId(userId);
        return new ResponseEntity<>(new ApiResponse< List < TransactionEntity > >(200, "All transactions fetched successfully", transactions), HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable String transactionId) {
        TransactionEntity transaction = transactionService.getTransactionById(transactionId);
        if(transaction == null) {
            return new ResponseEntity<> ( new ApiResponse<>(404, "transaction not found", null, false), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(200, "transaction fetched", transaction), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String transactionId) {
        boolean isDeleted = transactionService.deleteTransaction(transactionId);
        if(isDeleted == true){
            return new ResponseEntity<>(new ApiResponse<>(200, "Transaction deleted successfully", null, isDeleted), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse<>(404, "Transaction not found",null,isDeleted), HttpStatus.NOT_FOUND);}
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<?> updateTransaction(@PathVariable String transactionId, @RequestBody TransactionModel transaction) {
        boolean isUpdated = transactionService.updateTransaction(transactionId,  transaction);
        if(isUpdated == true){
            return new ResponseEntity<>(new ApiResponse<>(200, "Transaction updated successfully", null, isUpdated), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse<>(404, "Transaction not found",null, isUpdated), HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<?> getTransactionByWalletId(@PathVariable String walletId) {
        List<TransactionEntity> transactions = transactionService.getAllTransactionByWalletId(walletId);
        return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);
    }

    
    @GetMapping("/transaction")
    public ResponseEntity<?> getTransactionBetweenDates(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String walletId) {
        if(walletId == null || walletId.trim().isEmpty()){
            List<TransactionEntity> transactions = transactionService.getAllTransactionBetweenDateRange(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);

        }else{
            List<TransactionEntity> transactions = transactionService.getAllTransactionByWalletIdBetweenDateRange(walletId,startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);
        }
    }

    
    
    
}
