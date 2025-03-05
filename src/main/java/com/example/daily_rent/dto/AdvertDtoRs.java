package com.example.daily_rent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO для ответа с объявлением")
public class AdvertDtoRs {

    @Schema(description = "ID объявления")
    private Integer id;

    @Schema(description = "Цена")
    private BigDecimal price;

    @Schema(description = "Активно ли объявление")
    @JsonProperty("is_active")
    private Boolean isActive;

    @Schema(description = "Квартира")
    private ApartmentDto apartment;

    @Schema(description = "Описание")
    private String description;
}
