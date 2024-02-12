package ru.assignment.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.assignment.model.enums.WinCombinationWhenType;

import java.util.List;

@Data
public class WinCombinationInfo {
    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;

    private Integer count;

    private String group;

    private WinCombinationWhenType when;

    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;
}
