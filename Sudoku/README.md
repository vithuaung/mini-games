# Sudoku CLI - Developer Guide

## Requirements

- Java 21+
- Maven 3.8+

## Build

```bash
cd Sudoku
mvn package
```

Produces `target/sudoku.jar`.

## Run Tests

```bash
mvn test
```

58 tests across 9 test classes, all should pass.

## Run the Game

```bash
cd Sudoku
java -jar target/sudoku.jar
```

Or from an IDE, run `com.sudoku.Main`.

## How to Play

The game shows a 9x9 puzzle with 30 pre-filled numbers. Empty cells are shown as `_`.

```
Welcome to Sudoku!

Here is your puzzle:
    1 2 3 4 5 6 7 8 9
  A _ _ 2 _ _ _ 1 8 6
  B _ 8 _ _ 5 9 _ 4 _
  ...

Enter command (e.g., A3 4, C5 clear, hint, check, quit):
```

**Commands:**

- `B3 7` puts 7 into row B, column 3
- `C5 clear` removes whatever is in C5
- `hint` fills in one empty cell for you
- `check` scans for duplicates in any row, column, or 3x3 box
- `quit` exits the game

The game ends when all cells are filled correctly.

## Assumptions

- Placing a number does not validate it right away. Use `check` to find violations. This is on purpose, same as how real Sudoku works.
- The puzzle targets 30 pre-filled cells. Sometimes a few more stay in if removing them would break the unique solution guarantee.
- `Press any key to play again...` shows on win but you need to re-run the program to actually restart.
- Commands are not case-sensitive.
