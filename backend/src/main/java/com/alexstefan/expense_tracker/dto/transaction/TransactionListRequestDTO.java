package com.alexstefan.expense_tracker.dto.transaction;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;


public record TransactionListRequestDTO(
        @NotNull
        Integer year,

        @Nullable
        Integer month
){}
