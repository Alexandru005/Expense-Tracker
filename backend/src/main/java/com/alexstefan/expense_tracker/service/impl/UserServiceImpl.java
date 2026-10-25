package com.alexstefan.expense_tracker.service.impl;

import com.alexstefan.expense_tracker.dto.user.LoginRequestDTO;
import com.alexstefan.expense_tracker.dto.user.RegisterRequestDTO;
import com.alexstefan.expense_tracker.dto.user.UserResponseDTO;
import com.alexstefan.expense_tracker.entity.User;
import com.alexstefan.expense_tracker.repository.UserRepository;
import com.alexstefan.expense_tracker.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO register(RegisterRequestDTO requestDTO){
        if(userRepository.existsByEmail(requestDTO.email())){
            throw new RuntimeException("This email already exists!");
        }

        User user = new User();
        user.setEmail(requestDTO.email());
        user.setName(requestDTO.name());
        user.setPasswordHash(requestDTO.password());

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getBalance(),
                savedUser.getSavings()
        );
    }

    public UserResponseDTO login(LoginRequestDTO requestDTO){
        User user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow(() -> new RuntimeException("Email was not found"));

        if(!user.getPasswordHash().equals(requestDTO.password()))
            throw new RuntimeException("Password is incorect!");

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBalance(),
                user.getSavings()
        );
    }
}
