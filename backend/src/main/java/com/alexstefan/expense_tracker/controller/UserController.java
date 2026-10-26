package com.alexstefan.expense_tracker.controller;

import com.alexstefan.expense_tracker.constants.ApiConstants;
import com.alexstefan.expense_tracker.dto.user.LoginRequestDTO;
import com.alexstefan.expense_tracker.dto.user.RegisterRequestDTO;
import com.alexstefan.expense_tracker.dto.user.UserResponseDTO;
import com.alexstefan.expense_tracker.dto.user.AuthResponseDTO;
import com.alexstefan.expense_tracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(ApiConstants.PATH_REGISTER)
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO user){
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping(ApiConstants.PATH_LOGIN)
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO user){
        return ResponseEntity.ok(userService.login(user));
    }
}
