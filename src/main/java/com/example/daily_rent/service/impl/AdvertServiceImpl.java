package com.example.daily_rent.service.impl;

import com.example.daily_rent.dto.AdvertDto;
import com.example.daily_rent.dto.AdvertResponseDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.mapper.AdvertMapper;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;

    @Override
    public AdvertResponseDto save(AdvertDto dto) {
        Advert advert = advertMapper.toEntity(dto);
        return advertMapper.toDto(advertRepository.save(advert));
    }
}
