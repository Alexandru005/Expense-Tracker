package com.alexstefan.expense_tracker.dto.transaction;

import jakarta.validation.constraints.NotNull;

public record SavingsListRequestDTO(
        @NotNull
        Long idUser
){}
