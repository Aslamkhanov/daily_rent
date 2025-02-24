package com.example.daily_rent.service;

import com.example.daily_rent.dto.BookingDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Booking;
import com.example.daily_rent.entity.Client;
import com.example.daily_rent.exception.EntityNotFoundException;
import com.example.daily_rent.mapper.BookingMapper;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.repository.BookingRepository;
import com.example.daily_rent.repository.ClientRepository;
import com.example.daily_rent.service.inerfaces.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final AdvertRepository advertRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDto save(BookingDto dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Client with id " + dto.getClientId() + " not found"));
        Advert advert = advertRepository.findById(dto.getAdvertId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Advert with id " + dto.getAdvertId() + " not found"));
        Booking booking = bookingMapper.toEntity(dto, client, advert);
        Booking newBooking = bookingRepository.save(booking);
        return bookingMapper.toDto(newBooking);
    }
}
