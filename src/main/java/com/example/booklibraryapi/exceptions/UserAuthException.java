package com.example.booklibraryapi.exceptions;

public class UserAuthException extends RuntimeException {
    public UserAuthException(String message) {
        super(message);
    }
}
