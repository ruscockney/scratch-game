package ru.assignment.detectors;

import lombok.AllArgsConstructor;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;
import ru.assignment.model.config.WinCombinationInfo;
import ru.assignment.model.enums.WinCombinationWhenType;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SameSymbolsWinDetector implements WinDetector {

    private final Config config;

    @Override
    public Map<String, Map<String, String>> detect(Matrix matrix) {
        Map<String, Map<String, String>> symbolsWin = new HashMap<>();

        for (Map.Entry<String, Integer> symbolFreqEntry : matrix.getFreqMap().entrySet()) {
            Map<String, Integer> bestCountByGroup = new HashMap<>();
            Map<String, String> winCombinationByGroup = new HashMap<>();
            for (Map.Entry<String, WinCombinationInfo> winCombinationInfoEntry : config.getWinCombinations().entrySet()) {
                String group = winCombinationInfoEntry.getValue().getGroup();
                Integer count = winCombinationInfoEntry.getValue().getCount();
                if (winCombinationInfoEntry.getValue().getWhen() == WinCombinationWhenType.SAME_SYMBOLS
                        && count <= symbolFreqEntry.getValue()
                        && count > bestCountByGroup.getOrDefault(group, 0)) {
                    bestCountByGroup.put(group, count);
                    winCombinationByGroup.put(group, winCombinationInfoEntry.getKey());
                }
            }
            if (!winCombinationByGroup.isEmpty()) {
                symbolsWin.put(symbolFreqEntry.getKey(), winCombinationByGroup);
            }
        }
        return symbolsWin;
    }
}
