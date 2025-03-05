package com.example.daily_rent.service;

import com.example.daily_rent.dto.BookingCreateDto;
import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.dto.PageDto;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingDtoRs save(BookingCreateDto dto);

    PageDto<BookingDtoRs> bookClientByEmail(String email, Pageable pageable);
}
