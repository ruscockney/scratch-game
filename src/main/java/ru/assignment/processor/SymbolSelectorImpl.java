package ru.assignment.processor;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
public class SymbolSelectorImpl implements SymbolSelector {

    private final Random random;

    @Override
    public Optional<String> select(Map<String, Integer> probabilityMap) {
        int total = probabilityMap.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        int cumulativeProbability = 0;
        int randomValue = random.nextInt(total);
        for (Map.Entry<String, Integer> entry : probabilityMap.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue < cumulativeProbability) {
                return Optional.of(entry.getKey());
            }
        }

        //impossible return
        return Optional.empty();
    }
}
