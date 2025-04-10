package com.example.booklibraryapi.controller;

import com.example.booklibraryapi.model.AuthResponse;
import com.example.booklibraryapi.model.RefreshTokenDto;
import com.example.booklibraryapi.model.UserAuthRequest;
import com.example.booklibraryapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя", description = "Создаёт нового пользователя в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
    })
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody UserAuthRequest request) {
        return authService.register(request);
    }

    @Operation(summary = "Авторизация пользователя", description = "Авторизует пользователя и возвращает токены.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизован"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные", content = @Content)
    })
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody UserAuthRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Обновление access токена", description = "Обновляет access токен с использованием refresh токена.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access токен успешно обновлён"),
            @ApiResponse(responseCode = "401", description = "Недействительный refresh токен", content = @Content)
    })
    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenDto refreshToken) {
        return authService.refreshAccessToken(refreshToken.getRefreshToken());
    }
}
