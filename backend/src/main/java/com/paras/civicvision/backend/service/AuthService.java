package com.paras.civicvision.backend.service;

import com.paras.civicvision.backend.dto.LoginRequest;
import com.paras.civicvision.backend.dto.LoginResponse;
import com.paras.civicvision.backend.dto.RegisterRequest;
import com.paras.civicvision.backend.dto.RegisterResponse;
import com.paras.civicvision.backend.entity.User;
import com.paras.civicvision.backend.enums.Role;
import com.paras.civicvision.backend.exception.InvalidCredentialsException;
import com.paras.civicvision.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                    PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public LoginResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new InvalidCredentialsException("Invalid credentials"));

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        throw new InvalidCredentialsException("Invalid credentials");
    }

    return new LoginResponse("dummy-token");
}
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().name()
        );
    }
}