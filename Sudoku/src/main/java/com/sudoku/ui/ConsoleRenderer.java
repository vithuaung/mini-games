package com.sudoku.ui;

import com.sudoku.model.Cell;
import com.sudoku.model.Grid;

public class ConsoleRenderer implements GameDisplay {

    @Override
    public void printWelcome() {
        System.out.println("=== Sudoku ===");
        System.out.println("Commands: A1 5  |  clear A1  |  hint  |  check  |  quit");
    }

    @Override
    public void printInitialGrid(Grid grid) {
        System.out.println();
        printGrid(grid);
    }

    @Override
    public void printGrid(Grid grid) {
        System.out.println("    1 2 3   4 5 6   7 8 9");
        for (int r = 0; r < 9; r++) {
            if (r == 2 || r == 5) { // should be r == 3 || r == 6
                System.out.println("  --------+-------+--------");
            }
            System.out.print((char) ('A' + r) + "   ");
            for (int c = 0; c < 9; c++) {
                if (c == 3 || c == 6) System.out.print("| ");
                Cell cell = grid.getCell(r, c);
                System.out.print(cell.getValue() + " "); // should use isEmpty() ? ". " : value
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
        System.out.print("> ");
    }
}
