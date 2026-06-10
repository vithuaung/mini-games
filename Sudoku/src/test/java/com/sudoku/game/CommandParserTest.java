package com.sudoku.game;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    private final CommandParser parser = new CommandParser();

    @Test
    void parsePlaceCommandShortForm() {
        Optional<Command> cmd = parser.parse("A1 5");
        assertTrue(cmd.isPresent());
        assertInstanceOf(PlaceCommand.class, cmd.get());
        PlaceCommand place = (PlaceCommand) cmd.get();
        assertEquals(0, place.row(), "Row A should map to index 0");
        assertEquals(0, place.col(), "Column 1 should map to index 0");
        assertEquals(5, place.value());
    }

    @Test
    void parsePlaceCommandLongForm() {
        Optional<Command> cmd = parser.parse("place B3 7");
        assertTrue(cmd.isPresent());
        assertInstanceOf(PlaceCommand.class, cmd.get());
        PlaceCommand place = (PlaceCommand) cmd.get();
        assertEquals(1, place.row(), "Row B should map to index 1");
        assertEquals(2, place.col(), "Column 3 should map to index 2");
        assertEquals(7, place.value());
    }

    @Test
    void parseClearCommand() {
        Optional<Command> cmd = parser.parse("C5 clear");
        assertTrue(cmd.isPresent());
        assertInstanceOf(ClearCommand.class, cmd.get());
        ClearCommand clear = (ClearCommand) cmd.get();
        assertEquals(2, clear.row(), "Row C should map to index 2");
        assertEquals(4, clear.col(), "Column 5 should map to index 4");
    }

    @Test
    void parseHintCommand() {
        Optional<Command> cmd = parser.parse("hint");
        assertTrue(cmd.isPresent());
        assertInstanceOf(HintCommand.class, cmd.get());
    }

    @Test
    void parseCheckCommand() {
        Optional<Command> cmd = parser.parse("check");
        assertTrue(cmd.isPresent());
        assertInstanceOf(CheckCommand.class, cmd.get());
    }

    @Test
    void parseQuitCommand() {
        Optional<Command> cmd = parser.parse("quit");
        assertTrue(cmd.isPresent());
        assertInstanceOf(QuitCommand.class, cmd.get());
    }

    @Test
    void parseUnknownInputReturnsEmpty() {
        assertTrue(parser.parse("xyz").isEmpty());
        assertTrue(parser.parse("").isEmpty());
        assertTrue(parser.parse("Z9 5").isEmpty());
    }
}
