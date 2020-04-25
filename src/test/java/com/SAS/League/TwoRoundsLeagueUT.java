package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoRoundsLeagueUT {
    TwoRoundsLeague two = new TwoRoundsLeague();

    @Test
    void toStringTest() {

        Assert.assertTrue(two.toString().equals("Two rounds league"));
    }

    @Test
    void howManyRounds() {
        Assert.assertTrue(two.howManyRounds() == 2);
    }
}