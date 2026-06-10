package com.sudoku.engine;

import com.sudoku.model.Grid;

import java.util.Optional;

public interface Validator {
    Optional<String> findViolation(Grid grid);
    boolean isValid(Grid grid);
}
