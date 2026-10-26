package com.alexstefan.expense_tracker.service;

import com.alexstefan.expense_tracker.dto.user.LoginRequestDTO;
import com.alexstefan.expense_tracker.dto.user.RegisterRequestDTO;
import com.alexstefan.expense_tracker.dto.user.UserResponseDTO;
import com.alexstefan.expense_tracker.dto.user.AuthResponseDTO;

public interface UserService {
    UserResponseDTO register(RegisterRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO requestDTO);
}
