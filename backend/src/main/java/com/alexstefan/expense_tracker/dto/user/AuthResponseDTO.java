package com.alexstefan.expense_tracker.dto.user;

import java.math.BigDecimal;

public record AuthResponseDTO(
        String token,
        Long id,
        String name,
        String email,
        BigDecimal balance,
        BigDecimal savings
) {}
