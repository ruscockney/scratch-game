package ru.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Matrix generateMatrix(String[][] data, String bonus) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (!data[i][j].equals(bonus)) {
                    freqMap.put(data[i][j], freqMap.getOrDefault(data[i][j], 0) + 1);
                }
            }
        }
        return new Matrix(data, freqMap, bonus);
    }

    public static Config parseConfig(String path) throws IOException {
        return new ObjectMapper().readValue(new File(path), Config.class);
    }
}
