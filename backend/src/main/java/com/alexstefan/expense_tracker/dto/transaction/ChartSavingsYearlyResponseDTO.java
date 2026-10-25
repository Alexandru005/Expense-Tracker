package com.alexstefan.expense_tracker.dto.transaction;

import java.math.BigDecimal;

public record ChartSavingsYearlyResponseDTO(
    String month,
    BigDecimal amount
){}
