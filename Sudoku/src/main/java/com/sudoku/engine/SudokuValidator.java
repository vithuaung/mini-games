package com.sudoku.engine;

import com.sudoku.model.Grid;

import java.util.Optional;

public class SudokuValidator implements Validator {

    @Override
    public Optional<String> findViolation(Grid grid) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean isValid(Grid grid) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
