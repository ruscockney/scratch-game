package ru.assignment.processor;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.assignment.Utils;
import ru.assignment.model.config.Config;
import ru.assignment.service.RewardCounter;
import ru.assignment.service.RewardCounterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RewardCounterImplTest {

    private RewardCounter rewardCounter;
    private Config config;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        config = Utils.parseConfig("./config.json");
        rewardCounter = new RewardCounterImpl(config);
    }

    @Test
    public void count_same_symbol_4_times_and_diagonally_left_to_right() {
        Map<String, List<String>> symbolsWin = new HashMap<>();
        symbolsWin.put("A", List.of("same_symbol_4_times"));
        symbolsWin.put("B", List.of("same_symbols_diagonally_left_to_right"));

        Double actualCount = rewardCounter.count(symbolsWin, "+1000", 10);

        assertEquals(3000, actualCount);
    }

    @Test
    public void count_same_symbol_4_times_and_diagonally_left_to_right_for_same_symbol() {
        Map<String, List<String>> symbolsWin = new HashMap<>();
        symbolsWin.put("A", List.of("same_symbol_4_times", "same_symbols_diagonally_left_to_right"));

        Double actualCount = rewardCounter.count(symbolsWin, "+1000", 10);

        assertEquals(4750, actualCount);
    }

    @Test
    public void count_comb_for_2_symbols() {
        Map<String, List<String>> symbolsWin = new HashMap<>();
        symbolsWin.put("A", List.of("same_symbol_4_times", "same_symbols_vertically"));
        symbolsWin.put("B", List.of("same_symbols_diagonally_left_to_right"));

        Double actualCount = rewardCounter.count(symbolsWin, "MISS", 10);

        assertEquals(2750, actualCount);
    }

    @Test
    public void count_without_win() {
        Map<String, List<String>> symbolsWin = new HashMap<>();

        Double actualCount = rewardCounter.count(symbolsWin, "+1000", 10);

        assertEquals(0, actualCount);
    }

}