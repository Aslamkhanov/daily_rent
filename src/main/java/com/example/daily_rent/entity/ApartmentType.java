package com.example.daily_rent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApartmentType {
    ONLY_ROOM("комната"),
    ONE_ROOM("1-комнатная квартира"),
    TWO_ROOM("2-комнатная квартира"),
    THREE_ROOM("3-комнатная квартира"),
    FOUR_OR_MORE_ROOM("4 и более комнатная квартира");

    private final String description;
}
