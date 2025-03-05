package com.example.daily_rent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO для пагинации")
public class PageDto<T> {

    @Schema(description = "Список элементов")
    private List<T> content;

    @Schema(description = "Номер страницы")
    private int number;

    @Schema(description = "Размер страницы")
    private Integer size;

    @Schema(description = "Общее количество элементов")
    private long totalElements;

    @Schema(description = "Общее количество страниц")
    private int totalPages;
}
