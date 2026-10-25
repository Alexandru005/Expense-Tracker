package com.alexstefan.expense_tracker.dto.transaction;

import com.alexstefan.expense_tracker.constants.TransactionCategory;
import com.alexstefan.expense_tracker.constants.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@NotBlank
@NotNull
public record AddTransactionRequestDTO(

        @NotNull
        Long userId,

        @NotBlank(message = "Title required")
        String title,

        @NotNull
        TransactionType type,

        @NotNull
        TransactionCategory category,

        @NotNull
        BigDecimal amount
){}
