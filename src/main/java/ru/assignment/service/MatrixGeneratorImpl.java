package ru.assignment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.assignment.model.Matrix;
import ru.assignment.model.config.Config;
import ru.assignment.model.config.SymbolsProbabilities;
import ru.assignment.processor.SymbolSelector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@AllArgsConstructor
public class MatrixGeneratorImpl implements MatrixGenerator {

    private final Config config;
    private final Random random;
    private final SymbolSelector symbolSelector;

    @Override
    public Matrix generate() {
        log.info("Start of matrix generation");

        String[][] matrixData = new String[config.getRows()][config.getColumns()];

        //for bonus
        int randomRow = random.nextInt(config.getRows());
        int randomColumn = random.nextInt(config.getColumns());
        String bonusSymbol = symbolSelector
                .select(config.getProbabilities().getBonusSymbols().getSymbols())
                .orElseThrow(() -> new RuntimeException("Exception during bonus symbol generation"));
        matrixData[randomRow][randomColumn] = bonusSymbol;

        //for standard
        Map<String, Integer> freqMap = new HashMap<>();
        for (SymbolsProbabilities probabilities : config.getProbabilities().getStandardSymbols()) {
            if (matrixData[probabilities.getRow()][probabilities.getColumn()] == null) {
                String standardSymbol = symbolSelector.select(probabilities.getSymbols())
                        .orElseThrow(() -> new RuntimeException("Exception during standard symbol generation"));
                matrixData[probabilities.getRow()][probabilities.getColumn()] = standardSymbol;
                freqMap.put(standardSymbol, freqMap.getOrDefault(standardSymbol, 0) + 1);
            }
        }

        log.info("End of matrix generation");

        return new Matrix(matrixData, freqMap, bonusSymbol);
    }
}
