package ru.assignment.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Config {
    private Integer columns;

    private Integer rows;

    private Map<String, SymbolInfo> symbols;

    private Probabilities probabilities;

    @JsonProperty("win_combinations")
    private Map<String, WinCombinationInfo> winCombinations;
}
