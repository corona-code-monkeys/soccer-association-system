package com.SAS.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameReportUT {

    private GameReport gameReportFull;
    private GameReport gameReportEmpty;


    @BeforeEach
    void setUp() {
        gameReportFull = new GameReport(LocalDate.parse("2020-04-01"), "A game between Maccabi Tel Aviv and Hapoel Hadera");
        gameReportEmpty = new GameReport();
    }

    @Test
    void getGameDateNotNull() {
        LocalDate date = gameReportFull.getGameDate();
        assertNotNull(date);
    }

    @Test
    void getGameDateNull() {
        LocalDate date = gameReportEmpty.getGameDate();
        assertNull(date);
    }

    @Test
    void editReportNotNull() {
        Boolean edited = gameReportFull.editReport("Maccabi Tel Aviv won the game");
        assertTrue(edited);
    }

    @Test
    void editReportNull() {
        Boolean edited = gameReportFull.editReport(null);
        assertFalse(edited);
    }
}