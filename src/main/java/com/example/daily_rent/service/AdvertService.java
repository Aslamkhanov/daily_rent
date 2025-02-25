package com.example.daily_rent.service;

import com.example.daily_rent.dto.AdvertDto;
import com.example.daily_rent.dto.AdvertResponseDto;

public interface AdvertService {
    AdvertResponseDto save(AdvertDto dto);
}
