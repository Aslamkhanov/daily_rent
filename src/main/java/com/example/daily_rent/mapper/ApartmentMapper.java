package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.ApartmentDto;
import com.example.daily_rent.entity.Apartment;
import com.example.daily_rent.entity.ApartmentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {
    @Mapping(target = "adverts", ignore = true)
    @Mapping(target = "type", source = "type", qualifiedByName = "getApartmentType")
    Apartment toEntity(ApartmentDto apartmentDto);

    ApartmentDto toDto(Apartment apartment);

    @Named("getApartmentType")
    default ApartmentType getApartmentType(String apartmentType) {
        return ApartmentType.valueOf(apartmentType);
    }
}
