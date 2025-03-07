package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.AdvertCreateDto;
import com.example.daily_rent.dto.AdvertDtoRs;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Apartment;
import com.example.daily_rent.exception.EntityNotFoundException;
import com.example.daily_rent.repository.ApartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AdvertMapper {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "apartment", source = "apartmentId", qualifiedByName = "getApartmentById")
    public abstract Advert toEntity(AdvertCreateDto dto);

    public abstract AdvertDtoRs toDto(Advert advert);

    @Named("getApartmentById")
    protected Apartment getApartmentById(Integer id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Apartment with id " + id + " not found"));
    }
}
