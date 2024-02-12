package ru.assignment.model.config;

import lombok.Data;

import java.util.Map;

@Data
public class SymbolsProbabilities {
    private Integer column;
    private Integer row;
    private Map<String, Integer> symbols;
}
