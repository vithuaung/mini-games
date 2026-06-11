# Sudoku CLI — Developer Guide

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

56 tests across 9 test classes, all should pass.

## Run the Game

```bash
java -jar target/sudoku.jar
```

Or from an IDE, run `com.sudoku.Main`.

## How to Play

The game shows a 9×9 puzzle with 30 pre-filled numbers. Empty cells are shown as `_`.

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

- `B3 7` — place 7 in row B, column 3
- `C5 clear` — clear cell C5
- `hint` — reveals one correct number
- `check` — checks for rule violations (duplicate in row, column, or 3×3 box)
- `quit` — exit the game

The game ends when the grid is completely and correctly filled.

## Assumptions

- Placing a number does not immediately validate it. Use `check` to find violations. This is intentional — it matches how Sudoku is normally played.
- The puzzle targets 30 pre-filled cells. The generator may leave slightly more if removing a clue would create multiple solutions, but uniqueness is always guaranteed.
- `Press any key to play again...` is shown on win but restarting requires re-running the program.
- Commands are case-insensitive.
