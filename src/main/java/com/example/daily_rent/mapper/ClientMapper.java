package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    @Mapping(target = "bookings", ignore = true)
    Client toEntity(ClientDto dto);
    ClientDto toDto(Client client);
}
