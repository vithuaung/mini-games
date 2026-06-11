# Design Notes

## Structure

The code is split into four packages, each with one job:

- `model` - data classes for the grid and cells
- `engine` - puzzle generation, solving, and validation
- `game` - the game loop and command parsing
- `ui` - all console output

## Design choices

Commands are modelled as a sealed interface with records (`PlaceCommand`, `ClearCommand`, etc.). The compiler checks that every command type is handled and no case is accidentally skipped.

`GameEngine` depends on `Solver` and `Validator` interfaces, not concrete classes. This made it easy to swap in stubs during testing instead of running the real solver on every test.

`GameDisplay` is also an interface. Tests use a recording stub that captures messages without touching stdout.

`MoveResult` uses named static methods (`success()`, `preFilled()`) instead of constructors, which makes the intent clear at the call site.

The backtracking solver stops early if it finds more than one solution. This is how unique-solution puzzles are guaranteed during clue removal.

## SOLID

- **Single Responsibility** - each class does one thing. `SudokuGenerator` only generates, `SudokuValidator` only validates.
- **Open/Closed** - a new display or solver implementation can be added without touching the engine.
- **Interface Segregation** - `Solver` and `Validator` are small, focused interfaces.
- **Dependency Inversion** - `GameEngine` is wired to interfaces. Concrete classes are only named in `Main`.

## Assumptions

- The puzzle targets 30 pre-filled cells. If removing a clue would allow more than one solution, that clue stays in, so the count may be slightly above 30.
- Placing a number does not immediately check for violations. The player uses `check` when they want to inspect the grid. This matches how Sudoku is normally played on paper.
- `Press any key to play again...` reads the next line of input and starts a fresh game.
- Commands are not case-sensitive.
