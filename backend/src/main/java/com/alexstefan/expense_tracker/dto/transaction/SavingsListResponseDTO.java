package com.alexstefan.expense_tracker.dto.transaction;

import com.alexstefan.expense_tracker.constants.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingsListResponseDTO(
        String title,
        LocalDate date,
        BigDecimal amount,
        TransactionType type
){}
