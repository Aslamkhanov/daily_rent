package com.example.daily_rent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO для клиента")
public class ClientDto {

    @Schema(description = "ID клиента")
    private Integer id;

    @Schema(description = "Имя клиента")
    private String name;

    @Schema(description = "Email клиента")
    private String email;
}
