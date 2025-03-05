package com.example.daily_rent.service;

import com.example.daily_rent.dto.AdvertCreateDto;
import com.example.daily_rent.dto.AdvertDtoRs;
import com.example.daily_rent.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdvertService {
    AdvertDtoRs save(AdvertCreateDto dto);

    PageDto<AdvertDtoRs> getAdvertsByCity(String city, Pageable pageable);
}
