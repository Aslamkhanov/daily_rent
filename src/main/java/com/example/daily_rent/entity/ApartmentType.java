package com.example.daily_rent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApartmentType {
    ONLY_ROOM("комната"),
    ONE_ROOM("1-комнатная"),
    TWO_ROOM("2-комнатная"),
    THREE_ROOM("3-комнатная"),
    FOUR_OR_MORE_ROOM("4 и более комнатная");

    private final String description;
}
