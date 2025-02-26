package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.CreateBookingDto;
import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Booking;
import com.example.daily_rent.entity.Client;
import com.example.daily_rent.exception.EntityNotFoundException;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.repository.ClientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookingMapper {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdvertRepository advertRepository;

    @Mapping(target = "client", source = "clientId", qualifiedByName = "getClientById")
    @Mapping(target = "advert", source = "advertId", qualifiedByName = "getAdvertById")
    @Mapping(target = "price", ignore = true)
    public abstract Booking toEntity(CreateBookingDto dto);

    public abstract BookingDtoRs toDto(Booking booking);

    @Named("getClientById")
    protected Client getClientById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Client with id " + id + " not found"));
    }

    @Named("getAdvertById")
    protected Advert getAdvertById(Integer id) {
        return advertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Advert with id " + id + " not found"));
    }
}
