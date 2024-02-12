package ru.assignment.facade;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.assignment.Utils;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;
import ru.assignment.model.output.Result;
import ru.assignment.service.MatrixGenerator;
import ru.assignment.service.RewardCounterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameFacadeImplTest {

    private Config config;
    private GameFacade gameFacade;
    private MatrixGenerator matrixGenerator;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        config = Utils.parseConfig("./config.json");
        matrixGenerator = mock(MatrixGenerator.class);
        gameFacade = new GameFacadeImpl(config, matrixGenerator, new RewardCounterImpl(config));
    }

    @Test
    public void execute_win_comb_1() {
        String[][] data = {{"A", "A", "A"}, {"C", "F", "D"}, {"A", "D", "MISS"}};
        Matrix matrix = Utils.generateMatrix(data, "MISS");

        when(matrixGenerator.generate()).thenReturn(matrix);

        Result actualResult = gameFacade.execute(100.0);

        Map<String, List<String>> expectedWinCombinations = new HashMap<>();
        expectedWinCombinations.put("A", List.of("same_symbol_4_times", "same_symbols_horizontally"));
        Result expectedResult = Result.builder()
                .matrix(data)
                .appliedBonusSymbol("MISS")
                .appliedWinningCombinations(expectedWinCombinations)
                .reward(15000.0)
                .build();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_win_comb_2() {
        String[][] data = {{"A", "E", "A"}, {"C", "A", "D"}, {"A", "D", "10x"}};
        Matrix matrix = Utils.generateMatrix(data, "10x");

        when(matrixGenerator.generate()).thenReturn(matrix);

        Result actualResult = gameFacade.execute(100.0);

        Map<String, List<String>> expectedWinCombinations = new HashMap<>();
        expectedWinCombinations.put("A", List.of("same_symbol_4_times", "same_symbols_diagonally_right_to_left"));
        Result expectedResult = Result.builder()
                .matrix(data)
                .appliedBonusSymbol("10x")
                .appliedWinningCombinations(expectedWinCombinations)
                .reward(375000.0)
                .build();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_win_comb_3() {
        String[][] data = {{"A", "E", "E"}, {"A", "E", "D"}, {"A", "D", "+500"}};
        Matrix matrix = Utils.generateMatrix(data, "+500");

        when(matrixGenerator.generate()).thenReturn(matrix);

        Result actualResult = gameFacade.execute(100.0);

        Map<String, List<String>> expectedWinCombinations = new HashMap<>();
        expectedWinCombinations.put("A", List.of("same_symbol_3_times", "same_symbols_vertically"));
        expectedWinCombinations.put("E", List.of("same_symbol_3_times"));
        Result expectedResult = Result.builder()
                .matrix(data)
                .appliedBonusSymbol("+500")
                .appliedWinningCombinations(expectedWinCombinations)
                .reward(10800.0)
                .build();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_win_comb_4() {
        String[][] data = {{"A", "B", "E"}, {"C", "E", "D"}, {"A", "D", "+500"}};
        Matrix matrix = Utils.generateMatrix(data, "+500");

        when(matrixGenerator.generate()).thenReturn(matrix);

        Result actualResult = gameFacade.execute(100.0);

        Map<String, List<String>> expectedWinCombinations = new HashMap<>();
        Result expectedResult = Result.builder()
                .matrix(data)
                .appliedBonusSymbol(null)
                .appliedWinningCombinations(expectedWinCombinations)
                .reward(0.0)
                .build();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_win_comb_5() {
        String[][] data = {{"A", "B", "A"}, {"C", "A", "D"}, {"A", "+500", "A"}};
        Matrix matrix = Utils.generateMatrix(data, "+500");

        when(matrixGenerator.generate()).thenReturn(matrix);

        Result actualResult = gameFacade.execute(100.0);

        Map<String, List<String>> expectedWinCombinations = new HashMap<>();
        expectedWinCombinations.put("A", List.of("same_symbol_5_times", "same_symbols_diagonally_right_to_left",
                "same_symbols_diagonally_left_to_right"));
        Result expectedResult = Result.builder()
                .matrix(data)
                .appliedBonusSymbol("+500")
                .appliedWinningCombinations(expectedWinCombinations)
                .reward(250500.0)
                .build();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_win_comb_6() {
        String[][] data = {{"A", "B", "E", "B"}, {"C", "A", "D", "D"}, {"A", "+500", "E", "D"}, {"A", "F", "E", "F"}};
        Matrix matrix = Utils.generateMatrix(data, "+500");

        when(matrixGenerator.generate()).thenReturn(matrix);

        Result actualResult = gameFacade.execute(100.0);

        Map<String, List<String>> expectedWinCombinations = new HashMap<>();
        expectedWinCombinations.put("A", List.of("same_symbol_4_times"));
        expectedWinCombinations.put("E", List.of("same_symbol_3_times"));
        expectedWinCombinations.put("D", List.of("same_symbol_3_times"));
        Result expectedResult = Result.builder()
                .matrix(data)
                .appliedBonusSymbol("+500")
                .appliedWinningCombinations(expectedWinCombinations)
                .reward(8800.0)
                .build();

        assertEquals(expectedResult, actualResult);
    }
}