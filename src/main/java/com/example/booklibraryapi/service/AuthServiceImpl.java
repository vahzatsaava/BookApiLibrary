package com.example.booklibraryapi.service;

import com.example.booklibraryapi.entity.User;
import com.example.booklibraryapi.exceptions.UserAuthException;
import com.example.booklibraryapi.model.AuthResponse;
import com.example.booklibraryapi.model.UserAuthRequest;
import com.example.booklibraryapi.repository.UserRepository;
import com.example.booklibraryapi.security.CustomUserDetails;
import com.example.booklibraryapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse register(UserAuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAuthException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        String token = jwtUtil.generateToken(new CustomUserDetails(user.getId(),
                user.getEmail(), user.getPassword(), new ArrayList<>())
        );

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(UserAuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserAuthException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(new CustomUserDetails(user.getId(),
                user.getEmail(), user.getPassword(), new ArrayList<>())
        );

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);
        log.error(username);
        if (username == null || jwtUtil.isTokenExpired(refreshToken)) {
            throw new UserAuthException("Invalid refresh token");
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDetails userDetails = new CustomUserDetails(user.getId(),
                user.getEmail(), user.getPassword(), new ArrayList<>()
        );

        String newAccessToken = jwtUtil.generateToken(userDetails);

        return new AuthResponse(newAccessToken);
    }


}
