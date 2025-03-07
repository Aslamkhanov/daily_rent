package com.example.daily_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO для ответа с бронированием")
public class BookingDtoRs {

    @Schema(description = "ID бронирования")
    private Integer id;

    @Schema(description = "Дата начала бронирования")
    @JsonProperty("date_start")
    private LocalDate startDate;

    @Schema(description = "Дата окончания бронирования")
    @JsonProperty("date_finish")
    private LocalDate endDate;

    @Schema(description = "Клиент")
    private ClientDto client;

    @Schema(description = "Объявление")
    private AdvertDtoRs advert;

    @Schema(description = "Цена")
    @JsonProperty("result_price")
    private BigDecimal price;
}
