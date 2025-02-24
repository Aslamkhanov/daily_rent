package com.example.daily_rent.dto;

import com.example.daily_rent.entity.ApartmentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDto {
    private Integer id;

    private String city;

    private String street;

    @JsonProperty("house")
    private String home;

    @JsonProperty("apartment_type")
    private ApartmentType type;
}
