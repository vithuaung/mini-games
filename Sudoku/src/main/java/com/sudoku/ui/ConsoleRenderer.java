package com.sudoku.ui;

import com.sudoku.model.Cell;
import com.sudoku.model.Grid;

public class ConsoleRenderer implements GameDisplay {

    @Override
    public void printWelcome() {
        System.out.println("Welcome to Sudoku!");
    }

    @Override
    public void printInitialGrid(Grid grid) {
        System.out.println();
        System.out.println("Here is your puzzle:");
        printGrid(grid);
    }

    @Override
    public void printGrid(Grid grid) {
        System.out.println("    1 2 3 4 5 6 7 8 9");
        for (int r = 0; r < 9; r++) {
            System.out.print("  " + (char) ('A' + r) + " ");
            for (int c = 0; c < 9; c++) {
                Cell cell = grid.getCell(r, c);
                System.out.print(cell.isEmpty() ? "_ " : cell.getValue() + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printPrompt() {
        System.out.print("\nEnter command (e.g., A3 4, C5 clear, hint, check, quit): ");
    }
}
