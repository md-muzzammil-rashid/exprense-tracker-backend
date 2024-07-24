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
import org.springframework.web.bind.annotation.RequestAttribute;
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
    public ResponseEntity<?> addTransactions(@RequestAttribute String userId , @RequestBody List<TransactionModel> transactions){
        // Add transaction logic here...
        List<TransactionEntity> savedTransactions = transactionService.addTransactions(transactions, userId);

       

        return new ResponseEntity<>(new ApiResponse<>(200, "saved", savedTransactions), HttpStatus.CREATED);
    }

    @GetMapping("/all-transactions")
    public ResponseEntity<?> getAllTransaction(@RequestAttribute String userId, @RequestParam(defaultValue="0")String pageNumber, @RequestParam(defaultValue="10")String pageSize) {
        List<TransactionEntity> transactions = transactionService.getAllTransactionByUserId(userId, Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        return new ResponseEntity<>(new ApiResponse<>(200, "All transactions fetched successfully", transactions), HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@RequestAttribute String userId, @PathVariable String transactionId) {
        TransactionEntity transaction = transactionService.getTransactionById(transactionId, userId);
        if(transaction == null) {
            return new ResponseEntity<> ( new ApiResponse<>(404, "transaction not found", null, false), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>(200, "transaction fetched", transaction), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@RequestAttribute String userId, @PathVariable String transactionId) {
        boolean isDeleted = transactionService.deleteTransaction(transactionId, userId);
        if(isDeleted){
            return new ResponseEntity<>(new ApiResponse<>(200, "Transaction deleted successfully", null, isDeleted), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse<>(404, "Transaction not found",null,isDeleted), HttpStatus.NOT_FOUND);}
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<?> updateTransaction(@RequestAttribute String userId, @PathVariable String transactionId, @RequestBody TransactionModel transaction) {
        boolean isUpdated = transactionService.updateTransaction(transactionId,  transaction, userId);
        if(isUpdated){
            return new ResponseEntity<>(new ApiResponse<>(200, "Transaction updated successfully", null, isUpdated), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse<>(404, "Transaction not found",null, isUpdated), HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<?> getTransactionByWalletId(@RequestAttribute String userId, @PathVariable String walletId, @RequestParam(defaultValue="0", required = false) String pageNumber, @RequestParam(defaultValue="10") String pageSize) {
        System.out.println(pageNumber);
        System.out.println(pageSize);
        List<TransactionEntity> transactions = transactionService.getAllTransactionByWalletId(walletId, userId, Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);
    }

    
    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactionBetweenDates(@RequestAttribute String userId, @RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String walletId) {
        if(walletId == null || walletId.trim().isEmpty()){
            List<TransactionEntity> transactions = transactionService.getAllTransactionBetweenDateRange(startDate, endDate, userId);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);

        }else{
            List<TransactionEntity> transactions = transactionService.getAllTransactionByWalletIdBetweenDateRange(walletId,startDate, endDate, userId);
            return new ResponseEntity<>(new ApiResponse<>(200, "Fetched Successfully", transactions), HttpStatus.OK);
        }
    }

    
    
    
}
