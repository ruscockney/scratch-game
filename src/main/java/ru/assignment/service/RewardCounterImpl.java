package ru.assignment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.assignment.model.config.Config;
import ru.assignment.model.enums.ImpactType;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class RewardCounterImpl implements RewardCounter {

    private final Config config;

    @Override
    public Double count(Map<String, List<String>> symbolsWin, String bonusSymbol, double bet) {
        log.info("Start of reward counting");

        if (symbolsWin.isEmpty()) {
            return 0.0;
        }

        double reward = 0;
        for (Map.Entry<String, List<String>> symbolWin : symbolsWin.entrySet()) {
            String symbol = symbolWin.getKey();
            double symbolReward = config.getSymbols().get(symbol).getRewardMultiplier();
            for (String win : symbolWin.getValue()) {
                symbolReward *= config.getWinCombinations().get(win).getRewardMultiplier();
            }
            reward += symbolReward;
        }
        reward *= bet;

        ImpactType impact = config.getSymbols().get(bonusSymbol).getImpact();
        switch (impact) {
            case EXTRA_BONUS:
                reward += config.getSymbols().get(bonusSymbol).getExtra();
                break;
            case MULTIPLY_REWARD:
                reward *= config.getSymbols().get(bonusSymbol).getRewardMultiplier();
                break;
            default:
                break;
        }

        log.info("Start of reward counting");

        return reward;
    }
}
