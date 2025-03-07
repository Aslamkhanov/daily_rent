package com.example.daily_rent.exception;

import lombok.experimental.StandardException;

@StandardException
public class BookingConflictException extends RuntimeException {
    public BookingConflictException(String message) {
        super(message);
    }
}
