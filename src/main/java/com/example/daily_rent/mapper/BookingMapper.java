package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.BookingDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Booking;
import com.example.daily_rent.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    @Mapping(target = "client", source = "client")
    @Mapping(target = "advert", source = "advert")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "price", ignore = true)
    Booking toEntity(BookingDto dto, Client client, Advert advert);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "advertId", source = "advert.id")
    BookingDto toDto(Booking booking);
}
