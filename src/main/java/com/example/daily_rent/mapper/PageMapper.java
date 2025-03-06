package com.example.daily_rent.mapper;

import com.example.daily_rent.dto.AdvertDtoRs;
import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.dto.PageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {AdvertMapper.class, BookingMapper.class})
public interface PageMapper {

    @Mapping(source = "content", target = "content")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "totalPages", target = "totalPages")
    PageDto<AdvertDtoRs> toPageAdvert(Page<AdvertDtoRs> page);

    @Mapping(source = "content", target = "content")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "totalPages", target = "totalPages")
    PageDto<BookingDtoRs> toPageBooking(Page<BookingDtoRs> page);
}