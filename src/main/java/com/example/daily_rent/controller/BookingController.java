package com.example.daily_rent.controller;

import com.example.daily_rent.dto.BookingCreateDto;
import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.dto.PageDto;
import com.example.daily_rent.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
@Tag(name = "Бронирование", description = "Контроллер для работы с бронированиями")
public class BookingController {
    private final BookingService bookingService;

    @Operation(summary = "Создание бронирования",
            description = "Создает новое бронирование для клиента",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Бронирование успешно создано",
                            content = @Content(schema = @Schema(implementation = BookingDtoRs.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoRs createBooking(@RequestBody BookingCreateDto dto) {
        return bookingService.save(dto);
    }

    @Operation(summary = "Получение бронирований по email клиента",
            description = "Возвращает список бронирований для указанного email клиента",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос",
                            content = @Content(schema = @Schema(implementation = PageDto.class))),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден")
            })
    @GetMapping
    public PageDto<BookingDtoRs> bookClientByEmail(@RequestParam String email,
                                                   Pageable pageable) {
        return bookingService.bookClientByEmail(email, pageable);
    }
}
