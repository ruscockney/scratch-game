package ru.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class Matrix {
    private String[][] data;
    private Map<String, Integer> freqMap;
    private String bonusSymbol;
}
