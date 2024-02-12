package ru.assignment.service;

import ru.assignment.model.Matrix;

public interface MatrixGenerator {
    /**
     *
     * @return generate Matrix object. Impl uses SymbolSelector.class for generating each symbol
     */
    Matrix generate();
}
