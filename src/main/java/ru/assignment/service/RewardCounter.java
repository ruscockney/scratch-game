package ru.assignment.service;

import java.util.List;
import java.util.Map;

public interface RewardCounter {
    /**
     *
     * @param symbolsWin Map like <"A":["same_symbol_3_times","same_symbols_horizontally"], "B":["same_symbol_4_times"]>
     * @param bonusSymbol
     * @param bet
     * @return Reward amount
     */
    Double count(Map<String, List<String>> symbolsWin, String bonusSymbol, double bet);
}
