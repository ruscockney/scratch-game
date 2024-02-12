package ru.assignment.facade;

import lombok.AllArgsConstructor;
import ru.assignment.detectors.LinearSymbolsWinDetector;
import ru.assignment.detectors.SameSymbolsWinDetector;
import ru.assignment.detectors.WinDetector;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;
import ru.assignment.model.output.Result;
import ru.assignment.service.MatrixGenerator;
import ru.assignment.service.RewardCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GameFacadeImpl implements GameFacade {

    private final Config config;
    private final MatrixGenerator matrixGenerator;
    private final RewardCounter rewardCounter;

    @Override
    public Result execute(Double bet) {

        //generate matrix
        Matrix matrix = matrixGenerator.generate();

        //detect win combinations and combine them to single map
        List<WinDetector> winDetectorList = new ArrayList<>();
        winDetectorList.add(new SameSymbolsWinDetector(config));
        winDetectorList.add(new LinearSymbolsWinDetector(config));

        Map<String, List<String>> winCombinationsBySymbol = new HashMap<>();
        for (WinDetector winDetector : winDetectorList) {
            Map<String, Map<String, String>> winCombinationsBySymbolAndGroup = winDetector.detect(matrix);
            for (Map.Entry<String, Map<String, String>> symbolEntry : winCombinationsBySymbolAndGroup.entrySet()) {
                winCombinationsBySymbol.putIfAbsent(symbolEntry.getKey(), new ArrayList<>());
                winCombinationsBySymbol.get(symbolEntry.getKey()).addAll(symbolEntry.getValue().values());
            }
        }

        //count reward
        Double reward = 0.0;
        if (!winCombinationsBySymbol.isEmpty()) {
            reward = rewardCounter.count(winCombinationsBySymbol, matrix.getBonusSymbol(), bet);
        }

        //build result
        return Result.builder()
                .matrix(matrix.getData())
                .reward(reward)
                .appliedWinningCombinations(winCombinationsBySymbol)
                .appliedBonusSymbol(winCombinationsBySymbol.isEmpty() ? null : matrix.getBonusSymbol())
                .build();
    }
}
