package com.alexstefan.expense_tracker.service;

import com.alexstefan.expense_tracker.dto.transaction.*;

import java.util.List;

public interface TransactionService {
    void addTransaction(AddTransactionRequestDTO requestDTO);

    List<TransactionListResponseDTO> getTransactionsListByTimeInterval(TransactionListRequestDTO requestDTO);
    List<SavingsListResponseDTO> getSavingsList(SavingsListRequestDTO requestDTO);

    List<ChartTransactionResponseDTO> getChartTransactionsDetails(ChartTransactionRequestDTO requestDTO);
    List<ChartSavingsYearlyResponseDTO> getYearlyChartSavingsDetails(ChartSavingsYearlyRequestDTO requestDTO);

}
