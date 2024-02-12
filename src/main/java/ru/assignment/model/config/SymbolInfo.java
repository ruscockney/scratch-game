package ru.assignment.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.assignment.model.enums.ImpactType;
import ru.assignment.model.enums.SymbolType;

@Data
public class SymbolInfo {
    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;

    private SymbolType type;

    private Double extra;

    private ImpactType impact;
}
