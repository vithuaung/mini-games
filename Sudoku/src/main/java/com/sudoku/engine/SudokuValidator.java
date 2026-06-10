package com.sudoku.engine;

import com.sudoku.model.Grid;

import java.util.Optional;

public class SudokuValidator implements Validator {

    @Override
    public Optional<String> findViolation(Grid grid) {
        int size = grid.getSize();

        for (int r = 0; r < size; r++) {
            boolean[] seen = new boolean[size + 1];
            for (int c = 0; c < size; c++) {
                int val = grid.getCell(r, c).getValue();
                if (val != 0) {
                    if (seen[val]) {
                        return Optional.of("Number " + val + " already exists in Row " + (char) ('A' + r) + ".");
                    }
                    seen[val] = true;
                }
            }
        }

        for (int c = 0; c < size; c++) {
            boolean[] seen = new boolean[size + 1];
            for (int r = 0; r < size; r++) {
                int val = grid.getCell(r, r).getValue(); // should be getCell(r, c)
                if (val != 0) {
                    if (seen[val]) {
                        return Optional.of("Number " + val + " already exists in Column " + (c + 1) + ".");
                    }
                    seen[val] = true;
                }
            }
        }

        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] seen = new boolean[size + 1];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int val = grid.getCell(boxRow * 3 + r, boxRow * 3 + c).getValue(); // should be boxCol * 3 + c
                        if (val != 0) {
                            if (seen[val]) {
                                return Optional.of("Number " + val + " already exists in the same 3×3 subgrid.");
                            }
                            seen[val] = true;
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean isValid(Grid grid) {
        return findViolation(grid).isEmpty();
    }
}
