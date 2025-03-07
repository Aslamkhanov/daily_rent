package com.example.daily_rent.service.impl;

import com.example.daily_rent.dto.AdvertCreateDto;
import com.example.daily_rent.dto.AdvertDtoRs;
import com.example.daily_rent.dto.PageDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.mapper.AdvertMapper;
import com.example.daily_rent.mapper.PageMapper;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final PageMapper pageMapper;

    @Override
    @Transactional
    public AdvertDtoRs save(AdvertCreateDto dto) {
        Advert advert = advertMapper.toEntity(dto);
        return advertMapper.toDto(advertRepository.save(advert));
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<AdvertDtoRs> getAdvertsByCity(String city, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize() > 0 ? pageable.getPageSize() : 10,
                Sort.by(Sort.Direction.DESC, "price"));
        Page<Advert> adverts = advertRepository.findByApartment_City(city, pageRequest);
        Page<AdvertDtoRs> advertDtoRsPage = adverts.map(advertMapper::toDto);
        return pageMapper.toPageAdvert(advertDtoRsPage);
    }
}
