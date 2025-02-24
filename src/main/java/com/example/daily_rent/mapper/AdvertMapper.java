package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.AdvertDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdvertMapper {
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "apartment", source = "apartment")
    Advert toEntity(AdvertDto dto, Apartment apartment);

    @Mapping(target = "apartmentId", source = "apartment.id")
    AdvertDto toDto(Advert advert);
}
