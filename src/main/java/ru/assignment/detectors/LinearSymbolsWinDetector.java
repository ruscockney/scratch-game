package ru.assignment.detectors;

import lombok.AllArgsConstructor;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;
import ru.assignment.model.config.WinCombinationInfo;
import ru.assignment.model.enums.WinCombinationWhenType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class LinearSymbolsWinDetector implements WinDetector {

    private final Config config;

    @Override
    public Map<String, Map<String, String>> detect(Matrix matrix) {
        Map<String, Map<String, String>> symbolsWin = new HashMap<>();

        for (Map.Entry<String, WinCombinationInfo> winCombinationInfoEntry : config.getWinCombinations().entrySet()) {
            String group = winCombinationInfoEntry.getValue().getGroup();

            if (winCombinationInfoEntry.getValue().getWhen() == WinCombinationWhenType.LINEAR_SYMBOLS) {
                List<List<String>> coveredAreas = winCombinationInfoEntry.getValue().getCoveredAreas();
                for (List<String> coveredArea : coveredAreas) {
                    Optional<String> winSymbol = checkCoveredArea(matrix, coveredArea);
                    winSymbol.ifPresent(s -> {
                        symbolsWin.putIfAbsent(s, new HashMap<>());
                        //compare rewards if there is the same group for this symbol
                        String existWinCombination = symbolsWin.get(s).get(group);
                        if (existWinCombination == null
                                || config.getWinCombinations().get(existWinCombination).getRewardMultiplier()
                                < winCombinationInfoEntry.getValue().getRewardMultiplier()) {
                            symbolsWin.get(s).put(group, winCombinationInfoEntry.getKey());
                        }
                    });
                }

            }
        }

        return symbolsWin;
    }

    private Optional<String> checkCoveredArea(Matrix matrix, List<String> coveredArea) {
        String prev = null;
        for (String cell : coveredArea) {
            String[] cellSplit = cell.split(":");
            int row = Integer.parseInt(cellSplit[0]);
            int column = Integer.parseInt(cellSplit[1]);
            if (prev == null) {
                prev = matrix.getData()[row][column];
            } else if (!prev.equals(matrix.getData()[row][column])) {
                return Optional.empty();
            }
        }
        return prev == null ? Optional.empty() : Optional.of(prev);
    }
}
