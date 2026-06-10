package com.sudoku.engine;

import java.util.Optional;

public interface Solver {
    Optional<int[][]> solve(int[][] puzzle);
    boolean hasUniqueSolution(int[][] puzzle);
}
