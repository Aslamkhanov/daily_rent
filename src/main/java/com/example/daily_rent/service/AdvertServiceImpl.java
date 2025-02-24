package com.example.daily_rent.service;

import com.example.daily_rent.dto.AdvertDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Apartment;
import com.example.daily_rent.exception.EntityNotFoundException;
import com.example.daily_rent.mapper.AdvertMapper;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.repository.ApartmentRepository;
import com.example.daily_rent.service.inerfaces.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;
    private final ApartmentRepository apartmentRepository;
    private final AdvertMapper advertMapper;

    @Override
    public AdvertDto save(AdvertDto dto) {
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Apartment with id " + dto.getApartmentId() + " not found"));
        Advert advert = advertMapper.toEntity(dto, apartment);
        Advert newAdvert = advertRepository.save(advert);
        return advertMapper.toDto(newAdvert);
    }
}
