package com.alexstefan.expense_tracker.service;

import com.alexstefan.expense_tracker.dto.transaction.*;
import com.alexstefan.expense_tracker.entity.User;

import java.util.List;

public interface TransactionService {
    void addTransaction(User user, AddTransactionRequestDTO requestDTO);

    List<TransactionListResponseDTO> getTransactionsListByTimeInterval(User user, TransactionListRequestDTO requestDTO);
    List<SavingsListResponseDTO> getSavingsList(User user, SavingsListRequestDTO requestDTO);

    List<ChartTransactionResponseDTO> getChartTransactionsDetails(User user, ChartTransactionRequestDTO requestDTO);
    List<ChartSavingsYearlyResponseDTO> getYearlyChartSavingsDetails(User user, ChartSavingsYearlyRequestDTO requestDTO);

}
