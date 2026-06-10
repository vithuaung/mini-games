package com.sudoku.model;

public class Grid {

    private static final int SIZE = 9;
    private final Cell[][] cells;

    public Grid(int[][] values, boolean[][] preFilled) {
        cells = new Cell[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                cells[r][c] = new Cell(values[r][c], preFilled[r][c]);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public int getSize() {
        return SIZE;
    }

    public boolean isComplete() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (cells[r][c].isEmpty()) return false;
            }
        }
        return true;
    }

    public void placeValue(int row, int col, int value) {
        cells[row][col].setValue(value);
    }

    public void clearCell(int row, int col) {
        cells[row][col].clear();
    }

    public int[][] toIntArray() {
        int[][] arr = new int[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                arr[r][c] = cells[r][c].getValue();
            }
        }
        return arr;
    }
}
