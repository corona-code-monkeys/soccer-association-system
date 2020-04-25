package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class NumberOfWinsUT {
    NumberOfWins wins = new NumberOfWins();

    @Test
    void  toStringTest() {
        Assert.assertTrue(wins.toString().equals("Number of wins"));
    }

    @Test
    void tieBreaker() {
        Assert.assertTrue(wins.tieBreaker().equals("Wins"));
    }
}