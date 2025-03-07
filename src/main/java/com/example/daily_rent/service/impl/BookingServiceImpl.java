package com.example.daily_rent.service.impl;

import com.example.daily_rent.dto.BookingCreateDto;
import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.dto.PageDto;
import com.example.daily_rent.entity.Booking;
import com.example.daily_rent.exception.BookingConflictException;
import com.example.daily_rent.exception.ClientNotFoundException;
import com.example.daily_rent.mapper.BookingMapper;
import com.example.daily_rent.mapper.PageMapper;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.repository.BookingRepository;
import com.example.daily_rent.repository.ClientRepository;
import com.example.daily_rent.service.BookingService;
import com.example.daily_rent.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final PageMapper pageMapper;
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final AdvertRepository advertRepository;

    @Override
    @Transactional(readOnly = true)
    public PageDto<BookingDtoRs> bookClientByEmail(String email, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize() > 0 ? pageable.getPageSize() : 20);
        Page<Booking> bookings = bookingRepository.findAllByClient_Email(email, pageRequest);
        Page<BookingDtoRs> bookingDtoRs = bookings.map(bookingMapper::toDto);
        return pageMapper.toPageBooking(bookingDtoRs);
    }

    @Override
    @Transactional
    public BookingDtoRs save(BookingCreateDto dto) {
        ClientDto clientDto = dto.getClient();
        if (clientDto == null || clientDto.getId() == null) {
            clientDto = clientService.save(clientDto != null ? clientDto : new ClientDto());
            dto.setClient(clientDto);
        } else if (!clientRepository.existsById(clientDto.getId())) {
            throw new ClientNotFoundException("Клиент с таким ID не найден");
        }

        if (isApartmentBooked(dto.getAdvertId(), dto.getStartDate(), dto.getEndDate())) {
            throw new BookingConflictException("Эта квартира уже занята в указанные даты.");
        }
        Booking booking = bookingMapper.toEntity(dto);
        BigDecimal totalPrice = calculatePrice(booking.getAdvert().getPrice(),
                booking.getStartDate(), booking.getEndDate());
        booking.setPrice(totalPrice);

        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    private boolean isApartmentBooked(Integer advertId, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.existsOverLappingBooking(advertId, startDate, endDate);
    }

    private BigDecimal calculatePrice(BigDecimal pricePerNight, LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return pricePerNight.multiply(BigDecimal.valueOf(daysBetween));
    }
}
