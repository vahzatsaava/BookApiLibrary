package com.example.booklibraryapi.service;


import com.example.booklibraryapi.model.AuthResponse;
import com.example.booklibraryapi.model.UserAuthRequest;

public interface AuthService {
    AuthResponse register(UserAuthRequest request);
    AuthResponse login(UserAuthRequest request);
    AuthResponse refreshAccessToken(String currentToken);
}
