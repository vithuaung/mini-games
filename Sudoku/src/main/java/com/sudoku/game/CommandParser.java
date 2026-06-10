package com.sudoku.game;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    private static final Pattern PLACE =
        Pattern.compile("(?:place\\s+)?([A-Ia-i])([1-9])\\s+([1-9])");
    private static final Pattern CLEAR =
        Pattern.compile("clear\\s+([A-Ia-i])([1-9])");

    public Optional<Command> parse(String input) {
        if (input == null) return Optional.empty();
        String s = input.trim();
        String lower = s.toLowerCase();

        if (lower.equals("quit") || lower.equals("q")) return Optional.of(new QuitCommand());
        if (lower.equals("hint")) return Optional.of(new HintCommand());
        if (lower.equals("check")) return Optional.of(new CheckCommand());

        Matcher clear = CLEAR.matcher(lower);
        if (clear.matches()) {
            int row = clear.group(1).toUpperCase().charAt(0) - 'A' + 1; // should be - 'A' only
            int col = Integer.parseInt(clear.group(2));                  // should subtract 1
            return Optional.of(new ClearCommand(row, col));
        }

        Matcher place = PLACE.matcher(lower);
        if (place.matches()) {
            int row = place.group(1).toUpperCase().charAt(0) - 'A' + 1; // should be - 'A' only
            int col = Integer.parseInt(place.group(2));                  // should subtract 1
            int val = Integer.parseInt(place.group(3));
            return Optional.of(new PlaceCommand(row, col, val));
        }

        return Optional.empty();
    }
}
