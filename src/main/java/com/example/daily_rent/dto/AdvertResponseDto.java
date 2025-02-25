package com.example.daily_rent.dto;

import com.example.daily_rent.entity.Apartment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertResponseDto {
    private Integer id;

    private BigDecimal price;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Apartment apartment;

    private String description;
}
