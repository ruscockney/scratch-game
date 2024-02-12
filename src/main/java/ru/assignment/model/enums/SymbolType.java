package ru.assignment.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SymbolType {
    STANDARD("standard"),
    BONUS("bonus");

    @JsonValue
    private final String value;

}
