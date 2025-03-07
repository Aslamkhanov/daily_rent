package com.example.daily_rent.dto;

import com.example.daily_rent.entity.ApartmentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto для квартиры")
public class ApartmentDto {

    @Schema(description = "ID квартиры")
    private Integer id;

    @Schema(description = "Город")
    private String city;

    @Schema(description = "Улица")
    private String street;

    @Schema(description = "Дом")
    private String house;

    @Schema(description = "Тип квартиры")
    @JsonProperty("apartment_type")
    private ApartmentType apartmentType;
}
