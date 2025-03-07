package com.example.daily_rent.exception;

import lombok.experimental.StandardException;

@StandardException
public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
