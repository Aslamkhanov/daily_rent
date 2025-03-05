package com.example.daily_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO для создания бронирования")
public class BookingCreateDto {

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

    @Schema(description = "ID объявления")
    @JsonProperty("advert_id")
    private Integer advertId;
}
