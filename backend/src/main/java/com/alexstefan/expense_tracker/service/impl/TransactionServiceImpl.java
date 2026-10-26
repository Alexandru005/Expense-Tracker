package com.alexstefan.expense_tracker.service.impl;

import com.alexstefan.expense_tracker.constants.TransactionCategory;
import com.alexstefan.expense_tracker.constants.TransactionType;
import com.alexstefan.expense_tracker.dto.transaction.*;
import com.alexstefan.expense_tracker.entity.Transaction;
import com.alexstefan.expense_tracker.entity.User;
import com.alexstefan.expense_tracker.repository.TransactionRepository;
import com.alexstefan.expense_tracker.repository.UserRepository;
import com.alexstefan.expense_tracker.service.TransactionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    //           OPERATIONS METHOD + HELPERS
    private void makeTransaction(User user, BigDecimal amount, TransactionType type){

        switch(type){
            case EXPENSE:
                if(amount.compareTo(user.getBalance()) > 0)
                    throw new RuntimeException("Insufficient balance for this expense");
                user.setBalance(user.getBalance().subtract(amount));
                break;

            case INCOME:
                user.setBalance(user.getBalance().add(amount));
                break;

            case SAVINGS_DEPOSIT:
                if(amount.compareTo(user.getBalance()) > 0)
                    throw new RuntimeException("Insufficient balance for this savings deposit");
                user.setBalance(user.getBalance().subtract(amount));
                user.setSavings(user.getSavings().add(amount));
                break;

            case SAVINGS_WITHDRAWAL:
                if(amount.compareTo(user.getSavings()) > 0)
                    throw new RuntimeException("Insufficient savings for this withdrawal");
                user.setBalance(user.getBalance().add(amount));
                user.setSavings(user.getSavings().subtract(amount));
        }

    }

    private Transaction createTransaction(User user, String title, TransactionType type,
                                          TransactionCategory category, BigDecimal amount){
        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setTitle(title);
        transaction.setType(type);
        transaction.setCategory(category);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());

        return transaction;
    }

    public void addTransaction(User principal, AddTransactionRequestDTO requestDTO){
        if (requestDTO.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Invalid amount");

        User user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        makeTransaction(user, requestDTO.amount(), requestDTO.type());
        userRepository.save(user);

        Transaction transaction = createTransaction(user, requestDTO.title(), requestDTO.type(),
                requestDTO.category(), requestDTO.amount());
        transactionRepository.save(transaction);
    }




    //          DISPLAY TRANSACTIONS
    public List<TransactionListResponseDTO> getTransactionsListByTimeInterval(User user, TransactionListRequestDTO requestDTO){
        if(requestDTO.month() != null && (requestDTO.month() < 1 || requestDTO.month() > 12))
            throw new RuntimeException("Invalid month!");

        List<Transaction> transactionList = transactionRepository.findTransactionsByTimeIntervalWhichAreNotWithdrawals(
                user.getId(), requestDTO.year(), requestDTO.month());

        if(transactionList.isEmpty())
            return Collections.emptyList();

        return transactionList.stream()
                            .map(transaction -> new TransactionListResponseDTO(
                                    transaction.getTitle(),
                                    transaction.getAmount(),
                                    transaction.getDate(),
                                    transaction.getCategory(),
                                    transaction.getType()
                            ))
                            .toList();
    }

    public List<SavingsListResponseDTO> getSavingsList(User user, SavingsListRequestDTO requestDTO){

        List<Transaction> savingsList = transactionRepository.findSavings(user.getId());

        if(savingsList.isEmpty())
            return Collections.emptyList();

        return savingsList.stream()
                .map(saving -> new SavingsListResponseDTO(
                        saving.getTitle(),
                        saving.getDate(),
                        saving.getAmount(),
                        saving.getType()
                ))
                .toList();
    }






    //          SHARE CHARTS INFO
    private List<ChartTransactionResponseDTO> createChartTransactionsList(HashMap<TransactionCategory, BigDecimal> categoryMap,
                                                                          BigDecimal totalExpenses){
        return categoryMap.entrySet().stream()
                .map(entry -> new ChartTransactionResponseDTO(
                        entry.getKey().toString(),
                        entry.getValue()
                                .divide(totalExpenses, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100))
                                .setScale(2, RoundingMode.HALF_UP),
                        entry.getValue()
                ))
                .collect(Collectors.toList());

    }

    public List<ChartTransactionResponseDTO> getChartTransactionsDetails(User user, ChartTransactionRequestDTO requestDTO){
        if(requestDTO.month() != null && (requestDTO.month() < 1 || requestDTO.month() > 12))
            throw new RuntimeException("Invalid month!");

        List<Transaction> listOfAllExpenses = transactionRepository.findTransactionsByTimeIntervalWhichAreExpenses(
                user.getId(), requestDTO.year(), requestDTO.month());

        if(listOfAllExpenses.isEmpty())
            return Collections.emptyList();

        HashMap<TransactionCategory, BigDecimal> categoryMap = new HashMap<>(
                listOfAllExpenses.stream()
                                 .collect(Collectors.groupingBy(Transaction::getCategory,
                                         Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)))
        );

        BigDecimal totalExpenses = categoryMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        return createChartTransactionsList(categoryMap, totalExpenses);
    }




    private List<ChartSavingsYearlyResponseDTO> createYearlyChartSavingsList(HashMap<String, BigDecimal> savingsMap){
        return savingsMap.entrySet().stream()
                                    .map(entry -> new ChartSavingsYearlyResponseDTO(
                                            entry.getKey(),
                                            entry.getValue()
                                    ))
                                    .toList();
    }

    public List<ChartSavingsYearlyResponseDTO> getYearlyChartSavingsDetails(User user, ChartSavingsYearlyRequestDTO requestDTO){

        List<Transaction> savingsInAYear = transactionRepository.getSavingsByYear(user.getId(), requestDTO.year());

        if(savingsInAYear.isEmpty())
            return Collections.emptyList();

        HashMap<String, BigDecimal> savingsDepositedMap = new HashMap<>(
                savingsInAYear.stream()
                        .collect(Collectors.groupingBy(transaction -> transaction.getDate().getMonth().toString(),
                                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)))
        );

        return createYearlyChartSavingsList(savingsDepositedMap);
    }


}
