package com.example.cryptoapp.service;

import com.example.cryptoapp.dto.AuthRequest;
import com.example.cryptoapp.dto.AuthResponse;
import com.example.cryptoapp.dto.RegisterRequest;
import com.example.cryptoapp.entity.User;
import com.example.cryptoapp.repository.UserRepository;
import com.example.cryptoapp.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    var user = userRepository.findByUsername(request.getUsername())
            .orElseThrow();
    var jwt = jwtService.generateToken(user.getUsername());

    return new AuthResponse(jwt);
    }
}