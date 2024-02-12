package ru.assignment.detectors;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.assignment.Utils;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LinearSymbolsWinDetectorTest {

    private LinearSymbolsWinDetector linearSymbolsWinDetector;
    private Config config;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        config = Utils.parseConfig("./config.json");
        linearSymbolsWinDetector = new LinearSymbolsWinDetector(config);
    }

    @Test
    public void detect_same_symbols_horizontally() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "A", "A"}, {"C", "F", "D"}, {"A", "D", "MISS"}}, "MISS");
        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("horizontally_linear_symbols", "same_symbols_horizontally");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void detect_same_symbols_vertically() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "C", "A"}, {"A", "F", "D"}, {"A", "D", "MISS"}}, "MISS");
        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("vertically_linear_symbols", "same_symbols_vertically");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void detect_same_symbols_diagonally_left_to_right() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "C", "A"}, {"C", "A", "D"}, {"E", "MISS", "A"}}, "MISS");
        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("ltr_diagonally_linear_symbols", "same_symbols_diagonally_left_to_right");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void detect_same_symbols_diagonally_right_to_left() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"E", "C", "A"}, {"C", "A", "D"}, {"A", "MISS", "A"}}, "MISS");
        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("rtl_diagonally_linear_symbols", "same_symbols_diagonally_right_to_left");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void detect_same_symbols_diagonally_right_to_left_and_horizontal() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "A", "A"}, {"C", "A", "D"}, {"A", "MISS", "E"}}, "MISS");
        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("rtl_diagonally_linear_symbols", "same_symbols_diagonally_right_to_left");
        expectedMap.get("A").put("horizontally_linear_symbols", "same_symbols_horizontally");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    void detect_2_same_symbols_horizontal() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "A", "A"}, {"C", "C", "C"}, {"A", "MISS", "E"}}, "MISS");
        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("horizontally_linear_symbols", "same_symbols_horizontally");
        expectedMap.put("C", new HashMap<>());
        expectedMap.get("C").put("horizontally_linear_symbols", "same_symbols_horizontally");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    void detect_all_comb_in_1_group() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "A", "A"}, {"A", "A", "A"}, {"A", "MISS", "A"}}, "MISS");

        config.getWinCombinations().get("same_symbols_horizontally").setGroup("group");
        config.getWinCombinations().get("same_symbols_vertically").setGroup("group");
        config.getWinCombinations().get("same_symbols_diagonally_left_to_right").setGroup("group");
        config.getWinCombinations().get("same_symbols_diagonally_right_to_left").setGroup("group");
        config.getWinCombinations().get("same_symbols_diagonally_right_to_left").setRewardMultiplier(7.0);

        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("group", "same_symbols_diagonally_right_to_left");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    void detect_strange_covered_area_for_horizontal() {
        Matrix matrix = Utils.generateMatrix(new String[][]{{"A", "B", "A"}, {"C", "D", "E"}, {"A", "MISS", "A"}}, "MISS");

        config.getWinCombinations().get("same_symbols_horizontally").setCoveredAreas(List.of(List.of("0:0", "0:2", "2:0", "2:2")));

        Map<String, Map<String, String>> actualMap = linearSymbolsWinDetector.detect(matrix);

        Map<String, Map<String, String>> expectedMap = new HashMap<>();
        expectedMap.put("A", new HashMap<>());
        expectedMap.get("A").put("horizontally_linear_symbols", "same_symbols_horizontally");

        assertEquals(expectedMap, actualMap);
    }

}