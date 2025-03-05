package com.example.daily_rent.controller;

import com.example.daily_rent.dto.ApartmentDto;
import com.example.daily_rent.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apartment")
@RequiredArgsConstructor
@Tag(name = "Квартира", description = "Контроллер для работы с квартирами")
public class ApartmentController {
    private final ApartmentService apartmentService;

    @Operation(summary = "Создание квартиры",
            description = "Создает новую квартиру",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Квартира успешно создана",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDto createApartment(@RequestBody ApartmentDto dto) {
        return apartmentService.save(dto);
    }
}