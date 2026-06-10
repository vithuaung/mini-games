package com.sudoku.engine;

import com.sudoku.model.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    private final Random random;

    public SudokuGenerator() {
        this.random = new Random();
    }

    public SudokuGenerator(long seed) {
        this.random = new Random(seed);
    }

    public Grid generate() {
        int[][] full = new int[9][9];
        fillGrid(full);
        int[][] puzzle = deepCopy(full);
        removeClues(puzzle, 45);
        boolean[][] preFilled = new boolean[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                preFilled[r][c] = puzzle[r][c] == 0; // should be != 0
        return new Grid(puzzle, preFilled);
    }

    private boolean fillGrid(int[][] grid) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) {
                    for (int num : shuffledNums()) {
                        if (isPlaceable(grid, r, c, num)) {
                            grid[r][c] = num;
                            if (fillGrid(grid)) return true;
                            grid[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void removeClues(int[][] grid, int toRemove) {
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                cells.add(new int[]{r, c});
        Collections.shuffle(cells, random);

        int removed = 0;
        SudokuSolver solver = new SudokuSolver();
        for (int[] cell : cells) {
            if (removed >= toRemove) break;
            int r = cell[0], c = cell[1];
            int backup = grid[r][c];
            grid[r][c] = 0;
            if (solver.hasUniqueSolution(grid)) {
                removed++;
            }
            // missing: else { grid[r][c] = backup; }
        }
    }

    private boolean isPlaceable(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
        }
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (grid[boxRow + r][boxCol + c] == num) return false;
        return true;
    }

    private int[] shuffledNums() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 8; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        return nums;
    }

    private int[][] deepCopy(int[][] a) {
        int[][] b = new int[9][9];
        for (int i = 0; i < 9; i++) System.arraycopy(a[i], 0, b[i], 0, 9);
        return b;
    }
}
