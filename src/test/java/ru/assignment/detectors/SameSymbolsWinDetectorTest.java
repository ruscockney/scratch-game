package ru.assignment.detectors;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.assignment.Utils;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SameSymbolsWinDetectorTest {

    private SameSymbolsWinDetector sameSymbolsWinDetector;
    private Config config;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        config = Utils.parseConfig("./config.json");
        sameSymbolsWinDetector = new SameSymbolsWinDetector(config);
    }

    @Test
    public void detect_no_same_symbols() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "B", "C"}, {"C", "F", "D"}, {"A", "D", "MISS"}}, "MISS");
        Map<String, Map<String, String>> actualMap = sameSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void detect_3_same_symbols() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "B", "C"}, {"A", "F", "D"}, {"A", "D", "MISS"}}, "MISS");
        Map<String, Map<String, String>> actualMap = sameSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("same_symbols", "same_symbol_3_times");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void detect_4_same_symbols() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "B", "C"}, {"A", "A", "D"}, {"A", "D", "MISS"}}, "MISS");
        Map<String, Map<String, String>> actualMap = sameSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("same_symbols", "same_symbol_4_times");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void detect_5_and_3_same_symbols() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "B", "C", "D"}, {"A", "A", "C", "D"}, {"A", "A", "C", "MISS"}}, "MISS");
        Map<String, Map<String, String>> actualMap = sameSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("same_symbols", "same_symbol_5_times");
        expectedMap.put("C", new HashMap<>());
        expectedMap.get("C").put("same_symbols", "same_symbol_3_times");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    void detect_5_and_5_same_symbols_from_diff_groups() {
        config.getWinCombinations().get("same_symbol_5_times").setGroup("diff_group");
        sameSymbolsWinDetector = new SameSymbolsWinDetector(config);

        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "B", "C"}, {"A", "A", "D"}, {"A", "A", "MISS"}}, "MISS");
        Map<String, Map<String, String>> actualMap = sameSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("diff_group", "same_symbol_5_times");
        expectedMap.get("A").put("same_symbols", "same_symbol_4_times");

        assertEquals(expectedMap, actualMap);
    }
}