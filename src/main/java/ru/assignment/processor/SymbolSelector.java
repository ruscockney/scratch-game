package ru.assignment.processor;

import java.util.Map;
import java.util.Optional;

public interface SymbolSelector {

    /**
     *
     * @param probabilityMap Map like <"A":1, "B":2, "C":3, "D":4>
     * @return selected symbol
     */
    Optional<String> select(Map<String, Integer> probabilityMap);
}
