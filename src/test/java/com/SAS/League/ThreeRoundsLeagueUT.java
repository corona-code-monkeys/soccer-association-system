package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class ThreeRoundsLeagueUT {
    ThreeRoundsLeague three = new ThreeRoundsLeague();

    @Test
    void toStringTest() {

        Assert.assertTrue(three.toString().equals("Three rounds league"));
    }

    @Test
    void howManyRounds() {
        Assert.assertTrue(three.howManyRounds() == 3);
    }
}