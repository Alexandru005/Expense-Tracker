package com.alexstefan.expense_tracker.service.impl;

import com.alexstefan.expense_tracker.dto.user.LoginRequestDTO;
import com.alexstefan.expense_tracker.dto.user.RegisterRequestDTO;
import com.alexstefan.expense_tracker.dto.user.UserResponseDTO;
import com.alexstefan.expense_tracker.dto.user.AuthResponseDTO;
import com.alexstefan.expense_tracker.entity.User;
import com.alexstefan.expense_tracker.repository.UserRepository;
import com.alexstefan.expense_tracker.security.JwtService;
import com.alexstefan.expense_tracker.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponseDTO register(RegisterRequestDTO requestDTO){
        String email = requestDTO.email().trim().toLowerCase();

        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("This email already exists!");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(requestDTO.name().trim());
        user.setPasswordHash(passwordEncoder.encode(requestDTO.password()));

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getBalance(),
                savedUser.getSavings()
        );
    }

    public AuthResponseDTO login(LoginRequestDTO requestDTO){
        String email = requestDTO.email().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, requestDTO.password())
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);


        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBalance(),
                user.getSavings()
        );
    }
}
