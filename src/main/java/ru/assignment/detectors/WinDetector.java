package ru.assignment.detectors;

import ru.assignment.model.Matrix;

import java.util.Map;

public interface WinDetector {
    /**
     *
     * @param matrix generated matrix
     * @return <symbol, <group, win_combination>> map which contains win combination names by groups for each symbol
     */
    Map<String, Map<String, String>> detect(Matrix matrix);
}
