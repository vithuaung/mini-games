package com.sudoku.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveResultTest {

    @Test
    void successResultHasCorrectStatus() {
        MoveResult result = MoveResult.success();
        assertEquals(MoveResult.Status.SUCCESS, result.status());
        assertTrue(result.isSuccess());
    }

    @Test
    void successResultHasAcceptedMessage() {
        MoveResult result = MoveResult.success();
        assertEquals("Move accepted.", result.message());
    }

    @Test
    void preFilledResultHasCorrectStatus() {
        MoveResult result = MoveResult.preFilled("A1");
        assertEquals(MoveResult.Status.PRE_FILLED, result.status());
        assertFalse(result.isSuccess());
    }

    @Test
    void preFilledResultMessageContainsCellName() {
        MoveResult result = MoveResult.preFilled("B3");
        assertTrue(result.message().contains("B3"));
    }

}
