package com.example.daily_rent.service;

import com.example.daily_rent.dto.CreateAdvertDto;
import com.example.daily_rent.dto.AdvertDtoRs;

public interface AdvertService {
    AdvertDtoRs save(CreateAdvertDto dto);
}
