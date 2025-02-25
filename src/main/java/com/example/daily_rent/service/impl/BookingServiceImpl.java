package com.example.daily_rent.service.impl;

import com.example.daily_rent.dto.BookingDto;
import com.example.daily_rent.dto.BookingResponseDto;
import com.example.daily_rent.entity.Booking;
import com.example.daily_rent.mapper.BookingMapper;
import com.example.daily_rent.repository.BookingRepository;
import com.example.daily_rent.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto save(BookingDto dto) {
        Booking booking = bookingMapper.toEntity(dto);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}
