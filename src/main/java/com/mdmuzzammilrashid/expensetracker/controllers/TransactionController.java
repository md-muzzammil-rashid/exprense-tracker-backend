package com.mdmuzzammilrashid.expensetracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.models.TransactionModel;
import com.mdmuzzammilrashid.expensetracker.services.ITransactionService;
import com.mdmuzzammilrashid.expensetracker.utils.ApiResponse;


@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    
    @Autowired
    ITransactionService transactionService;
    @PostMapping("/add-transactions")
    public ResponseEntity<?> addTransactions(@RequestBody List<TransactionModel> transactions){
        // Add transaction logic here...
        String userId = "user2";
        List<TransactionEntity> savedTransactions = transactionService.addTransactions(transactions, userId);

       

        return new ResponseEntity<>(new ApiResponse<>(200, "saved", savedTransactions), HttpStatus.CREATED);
    }

    @GetMapping("/all-transactions")
    public ResponseEntity<?> getAllTransaction() {
        // String userId = "8f028bb8-0569-42b9-a2c8-d0273cbfa31b";
        String userId = "user2";
        List<TransactionEntity> transactions = transactionService.getAllTransactionByUserId(userId);
        return new ResponseEntity<>(new ApiResponse<>(200, "All transactions fetched successfully", transactions), HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable String transactionId) {
        String userId = "user2";
        TransactionEntity transaction = transactionService.getTransactionById(transactionId, userId);
        if(transaction == null) {
            return new ResponseEntity<> ( new ApiResponse<>(404, "transaction not found", null, false), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(200, "transaction fetched", transaction), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String transactionId) {
        String userId = "user2";
        boolean isDeleted = transactionService.deleteTransaction(transactionId, userId);
        if(isDeleted){
            return new ResponseEntity<>(new ApiResponse<>(200, "Transaction deleted successfully", null, isDeleted), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse<>(404, "Transaction not found",null,isDeleted), HttpStatus.NOT_FOUND);}
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<?> updateTransaction(@PathVariable String transactionId, @RequestBody TransactionModel transaction) {
        String userId = "user2";
        boolean isUpdated = transactionService.updateTransaction(transactionId,  transaction, userId);
        if(!isUpdated){
            return new ResponseEntity<>(new ApiResponse<>(200, "Transaction updated successfully", null, isUpdated), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse<>(404, "Transaction not found",null, isUpdated), HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<?> getTransactionByWalletId(@PathVariable String walletId, @RequestParam(required=false) Integer limit) {
        String userId = "user2";
        if(limit==null) limit =10;
        List<TransactionEntity> transactions = transactionService.getAllTransactionByWalletId(walletId, userId, limit);
        return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);
    }

    
    @GetMapping("/transaction")
    public ResponseEntity<?> getTransactionBetweenDates(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String walletId) {
        String userId = "user2";
        if(walletId == null || walletId.trim().isEmpty()){
            List<TransactionEntity> transactions = transactionService.getAllTransactionBetweenDateRange(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);

        }else{
            List<TransactionEntity> transactions = transactionService.getAllTransactionByWalletIdBetweenDateRange(walletId,startDate, endDate, userId);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);
        }
    }

    
    
    
}
