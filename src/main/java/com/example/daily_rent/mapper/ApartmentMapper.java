package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.ApartmentDto;
import com.example.daily_rent.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {
    @Mapping(target = "adverts", ignore = true)
    Apartment toEntity(ApartmentDto apartmentDto);

    ApartmentDto toDto(Apartment apartment);
}
