package com.alexstefan.expense_tracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        @Size(max = 50)
        String name,

        @NotBlank(message = "Email is required")
        @Email
        @Size(max = 128, message = "Email must not exceed 128 characters")
        @Size(max = 128)
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        @Size(min = 8, max = 72)
        String password
){}
