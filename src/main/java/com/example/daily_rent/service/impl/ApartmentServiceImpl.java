package com.example.daily_rent.service.impl;

import com.example.daily_rent.dto.ApartmentDto;
import com.example.daily_rent.entity.Apartment;
import com.example.daily_rent.mapper.ApartmentMapper;
import com.example.daily_rent.repository.ApartmentRepository;
import com.example.daily_rent.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    @Override
    public ApartmentDto save(ApartmentDto dto) {
        Apartment apartment = apartmentMapper.toEntity(dto);
        apartmentRepository.save(apartment);
        return apartmentMapper.toDto(apartment);
    }
}
