package ru.assignment.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Probabilities {
    @JsonProperty("standard_symbols")
    private List<SymbolsProbabilities> standardSymbols;

    @JsonProperty("bonus_symbols")
    private SymbolsProbabilities bonusSymbols;
}
