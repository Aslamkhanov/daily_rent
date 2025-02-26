package com.example.daily_rent.service;

import com.example.daily_rent.dto.CreateBookingDto;
import com.example.daily_rent.dto.BookingDtoRs;

public interface BookingService {
    BookingDtoRs save(CreateBookingDto dto);
}
