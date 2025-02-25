package com.example.daily_rent.dto;

import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Client;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDto {
    private Integer id;

    @JsonProperty("date_start")
    private LocalDate startDate;

    @JsonProperty("date_finish")
    private LocalDate endDate;

    private Client client;

    private Advert advert;

    @JsonProperty("result_price")
    private BigDecimal price;
}
