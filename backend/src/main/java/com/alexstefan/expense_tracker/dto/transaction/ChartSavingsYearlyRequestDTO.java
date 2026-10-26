package com.alexstefan.expense_tracker.dto.transaction;

import com.alexstefan.expense_tracker.constants.TransactionType;
import jakarta.validation.constraints.NotNull;

public record ChartSavingsYearlyRequestDTO(
        @NotNull
        Integer year
){}
