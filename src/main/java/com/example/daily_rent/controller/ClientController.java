package com.example.daily_rent.controller;

import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Tag(name = "Клиент", description = "Контроллер для работы с клиентами")
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "Создание клиента",
            description = "Создает нового клиента",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Клиент успешно создан",
                            content = @Content(schema = @Schema(implementation = ClientDto.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto createClient(@RequestBody ClientDto clientDto) {
        return clientService.save(clientDto);
    }

    @Operation(summary = "Удаление клиента",
            description = "Удаляет клиента по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Клиент успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден")
            })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable Integer id) {
        clientService.delete(id);
    }
}
