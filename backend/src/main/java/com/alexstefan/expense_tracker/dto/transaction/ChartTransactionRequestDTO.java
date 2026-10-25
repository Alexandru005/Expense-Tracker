package com.alexstefan.expense_tracker.dto.transaction;

import com.alexstefan.expense_tracker.constants.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;


public record ChartTransactionRequestDTO(
        @NotNull
        Long idUser,

        @NotNull
        Integer year,

        @Nullable
        Integer month
){}
