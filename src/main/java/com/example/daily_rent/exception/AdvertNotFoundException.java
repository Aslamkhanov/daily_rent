package com.example.daily_rent.exception;

import lombok.experimental.StandardException;

@StandardException
public class AdvertNotFoundException extends RuntimeException {
    public AdvertNotFoundException(String message) {
        super(message);
    }
}
