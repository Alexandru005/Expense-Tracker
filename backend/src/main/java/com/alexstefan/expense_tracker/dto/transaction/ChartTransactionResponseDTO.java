package com.alexstefan.expense_tracker.dto.transaction;

import java.math.BigDecimal;

public record ChartTransactionResponseDTO(
        String category,
        BigDecimal percentage,
        BigDecimal amount
){}
