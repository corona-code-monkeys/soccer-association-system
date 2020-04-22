package com.SAS.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberOfWinsTest {
    NumberOfWins wins = new NumberOfWins();

    @Test
    void  toStringTest() {
        Assertions.assertTrue(wins.toString().equals("Number of wins"));
    }

    @Test
    void tieBreaker() {
        Assertions.assertTrue(wins.tieBreaker().equals("Wins"));
    }
}