package com.alexstefan.expense_tracker.dto.transaction;

import com.alexstefan.expense_tracker.constants.TransactionCategory;
import com.alexstefan.expense_tracker.constants.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionListResponseDTO(
        String title,
        BigDecimal amount,
        LocalDate date,
        TransactionCategory category,
        TransactionType type
){}
