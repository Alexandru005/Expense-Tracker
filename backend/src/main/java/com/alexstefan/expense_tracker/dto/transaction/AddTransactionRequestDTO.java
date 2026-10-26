package com.alexstefan.expense_tracker.dto.transaction;

import com.alexstefan.expense_tracker.constants.TransactionCategory;
import com.alexstefan.expense_tracker.constants.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddTransactionRequestDTO(
        @NotBlank(message = "Title required")
        String title,

        @NotNull
        TransactionType type,

        @NotNull
        TransactionCategory category,

        @NotNull
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount
) {}
