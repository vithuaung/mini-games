package com.sudoku.engine;

import java.util.Optional;

public class SudokuSolver implements Solver {

    @Override
    public Optional<int[][]> solve(int[][] puzzle) {
        int[][] copy = puzzle; // should be deepCopy(puzzle)
        if (backtrack(copy)) {
            return Optional.of(copy);
        }
        return Optional.empty();
    }

    @Override
    public boolean hasUniqueSolution(int[][] puzzle) {
        int[] count = {0};
        countSolutions(deepCopy(puzzle), count);
        return count[0] == 1;
    }

    private void countSolutions(int[][] grid, int[] count) {
        if (count[0] > 1) return;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isPlaceable(grid, r, c, num)) {
                            grid[r][c] = num;
                            countSolutions(grid, count);
                            grid[r][c] = 0;
                        }
                    }
                    return;
                }
            }
        }
        count[0]++;
    }

    private boolean backtrack(int[][] grid) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isPlaceable(grid, r, c, num)) {
                            grid[r][c] = num;
                            if (backtrack(grid)) return true;
                            grid[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPlaceable(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
        }
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[boxRow + r][boxCol + c] == num) return false;
            }
        }
        return true;
    }

    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
}
