package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.dto.BookingCreateDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Booking;
import com.example.daily_rent.exception.EntityNotFoundException;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.repository.ClientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class})
public abstract class BookingMapper {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdvertRepository advertRepository;

    @Mapping(target = "advert", source = "advertId", qualifiedByName = "getAdvertById")
    @Mapping(target = "price", ignore = true)
    public abstract Booking toEntity(BookingCreateDto dto);

    public abstract BookingDtoRs toDto(Booking booking);

    @Named("getAdvertById")
    protected Advert getAdvertById(Integer id) {
        return advertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Advert with id " + id + " not found"));
    }
}
