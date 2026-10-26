package com.alexstefan.expense_tracker.controller;

import com.alexstefan.expense_tracker.constants.ApiConstants;
import com.alexstefan.expense_tracker.dto.transaction.AddTransactionRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.ChartSavingsYearlyRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.ChartSavingsYearlyResponseDTO;
import com.alexstefan.expense_tracker.dto.transaction.ChartTransactionRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.ChartTransactionResponseDTO;
import com.alexstefan.expense_tracker.dto.transaction.SavingsListRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.SavingsListResponseDTO;
import com.alexstefan.expense_tracker.dto.transaction.TransactionListRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.TransactionListResponseDTO;
import com.alexstefan.expense_tracker.service.TransactionService;
import com.alexstefan.expense_tracker.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.TRANSACTIONS_BASE)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> addTransaction(
            @Valid @RequestBody AddTransactionRequestDTO requestDTO,
            @AuthenticationPrincipal User user) {
        transactionService.addTransaction(user, requestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.TRANSACTION_LIST)
    public ResponseEntity<List<TransactionListResponseDTO>> getTransactions(
            @Valid @ModelAttribute TransactionListRequestDTO requestDTO,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.getTransactionsListByTimeInterval(user, requestDTO));
    }

    @GetMapping(ApiConstants.SAVINGS_LIST)
    public ResponseEntity<List<SavingsListResponseDTO>> getSavings(
            @Valid @ModelAttribute SavingsListRequestDTO requestDTO,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.getSavingsList(user, requestDTO));
    }

    @GetMapping(ApiConstants.EXPENSE_CHART)
    public ResponseEntity<List<ChartTransactionResponseDTO>> getExpenseChart(
            @Valid @ModelAttribute ChartTransactionRequestDTO requestDTO,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.getChartTransactionsDetails(user, requestDTO));
    }

    @GetMapping(ApiConstants.YEARLY_SAVINGS_CHART)
    public ResponseEntity<List<ChartSavingsYearlyResponseDTO>> getYearlySavingsChart(
            @Valid @ModelAttribute ChartSavingsYearlyRequestDTO requestDTO,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.getYearlyChartSavingsDetails(user, requestDTO));
    }
}
