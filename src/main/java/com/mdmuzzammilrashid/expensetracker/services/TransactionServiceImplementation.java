package com.mdmuzzammilrashid.expensetracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mdmuzzammilrashid.expensetracker.Entity.TransactionEntity;
import com.mdmuzzammilrashid.expensetracker.enums.TransactionType;
import com.mdmuzzammilrashid.expensetracker.models.TransactionModel;
import com.mdmuzzammilrashid.expensetracker.repositories.ITransactionRepo;
import com.mdmuzzammilrashid.expensetracker.repositories.IWalletRepo;

@Service
public class TransactionServiceImplementation implements ITransactionService {

    @Autowired
    private ITransactionRepo transactionRepository;

    @Autowired
    private IWalletServices walletService;

    @Autowired
    private IWalletRepo walletRepository;

    @Override
    public TransactionEntity getTransactionById(String transactionId, String userId) {

        //FIXME: verify user with tranasction
        Optional<TransactionEntity> transaction = transactionRepository.findById(transactionId);
        if(transaction.isPresent()){
            if(transaction.get().getUserId().equals(userId)){
                return transaction.get();
            }else{
                // Throw exception of unauthenticated query
            }
        }else{
            //TODO: throw exception
        }
        return null;
        
    }

    @Override
    public List<TransactionEntity> getAllTransactionByUserId(String userId) {
            return transactionRepository.findByUserId(userId);
    }
    

    @Override
    public List<TransactionEntity> getAllTransactionByWalletId(String walletId, String userId, Integer limit) {
        //TODO:
        try {
            if(walletService.verifyUserWithWallet(userId, walletId)==false){
                //TODO: throw exception of unauthorized user
                return null;
            }
            Pageable page = PageRequest.of(0, limit);
            Optional<List<TransactionEntity>> transactions = transactionRepository.findTransactionByWalletId(walletId, userId, limit);
            if(transactions.isPresent()){
                return transactions.get();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }


    @Override
    public List<TransactionEntity> addTransactions(List<TransactionModel> transactions, String userId) {
        
        List<TransactionEntity> savedTransactions = new ArrayList<>();
        transactions.forEach(transaction -> {
            if(!walletService.verifyUserWithWallet(userId, transaction.getWalletId())){
                //TODO: throw exception of unauthorized user
                return;
            }
            TransactionEntity entity = new TransactionEntity();
            if(transaction.getType().equals(TransactionType.INCOME)){
                walletService.incrementWalletAmount(transaction.getWalletId(), transaction.getAmount());

            }else if(transaction.getType().equals(TransactionType.EXPENSE)){
                walletService.decrementWalletAmount(transaction.getWalletId(), transaction.getAmount());
            }
            entity.setUserId(userId);
            entity.setAmount(transaction.getAmount());
            entity.setDate(transaction.getDate());
            entity.setCategory(transaction.getCategory());
            entity.setWalletId(transaction.getWalletId());
            entity.setDescription(transaction.getDescription());
            entity.setTransactionId(transaction.getTransactionId());
            entity.setType(transaction.getType());
            savedTransactions.add(entity);
        });
        transactionRepository.saveAll(savedTransactions);
        return savedTransactions;
    }

    @Override
    public Boolean updateTransaction(String transactionId, TransactionModel transaction, String userId) {
        try {
            Optional<TransactionEntity> oldTransaction = transactionRepository.findById(transactionId);
            if(oldTransaction.isPresent()){
                if(!oldTransaction.get().getUserId().equals(userId)){
                    //TODO: throw exception of unauthorized user
                    return false;
                }
                if(oldTransaction.get().getType().equals(TransactionType.INCOME)){
                    walletService.decrementWalletAmount(oldTransaction.get().getWalletId(), oldTransaction.get().getAmount());
                    walletService.incrementWalletAmount(oldTransaction.get().getWalletId(), transaction.getAmount());
                }else if(oldTransaction.get().getType().equals(TransactionType.EXPENSE)){
                    walletService.incrementWalletAmount(oldTransaction.get().getWalletId(), oldTransaction.get().getAmount());
                    walletService.decrementWalletAmount(oldTransaction.get().getWalletId(), transaction.getAmount());
                }
                oldTransaction.get().setAmount(transaction.getAmount());
                oldTransaction.get().setCategory(transaction.getCategory());
                oldTransaction.get().setDate(transaction.getDate());
                oldTransaction.get().setDescription(transaction.getDescription());
                oldTransaction.get().setWalletId(transaction.getWalletId());
                oldTransaction.get().setType(transaction.getType());
                transactionRepository.save(oldTransaction.get());
                return true;
            }            
        } catch (Exception e) {
            // TODO: handle exception

        }
        return null;
    }

    @Override
    public Boolean deleteTransaction(String transactionId, String userId) {
        try {
             Optional<TransactionEntity> transaction = transactionRepository.findById(transactionId);
            if(transaction.isPresent()){

                if(!transaction.get().getUserId().equals(userId)){
                    //TODO: throw exception of unauthorized user
                    return false;
                }
                if(transaction.get().getType().equals(TransactionType.EXPENSE)){
                    walletService.incrementWalletAmount(transaction.get().getWalletId(), transaction.get().getAmount());
                }else if(transaction.get().getType().equals(TransactionType.INCOME)){
                    walletService.decrementWalletAmount(transaction.get().getWalletId(), transaction.get().getAmount());
                }
                transactionRepository.deleteById(transactionId);
                Optional<TransactionEntity> deletedTransaction = transactionRepository.findById(transactionId);
                return !deletedTransaction.isPresent();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
        return false;
    }

    @Override
    public List<TransactionEntity> getAllTransactionBetweenDateRange(String startDate, String endDate) {
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
    public List<TransactionEntity> getAllTransactionByWalletIdBetweenDateRange(String walletId, String startDate,
            String endDate, String userId) {
                if(!walletService.verifyUserWithWallet(userId, walletId)){
                    // TODO: throw exception of unauthenticated
                    return null;
                }
                try {
                    Optional<List<TransactionEntity>> transactions = transactionRepository.findTransactionByWalletIdBetweenDates(walletId, startDate, endDate);
                    if(transactions.isPresent()) {
                        return transactions.get();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return null;
    }

    @Override
    public List<TransactionEntity> addIncome(List<TransactionEntity> transactions) {
        // TODO Auto-generated method stub
        transactions.forEach(transaction->{
            //TODO:
        });
        return null;
    }
}
