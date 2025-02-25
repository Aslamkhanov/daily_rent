package com.example.daily_rent.service;

import com.example.daily_rent.dto.BookingDto;
import com.example.daily_rent.dto.BookingResponseDto;

public interface BookingService {
    BookingResponseDto save(BookingDto dto);
}
