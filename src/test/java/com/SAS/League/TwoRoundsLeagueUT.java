package com.SAS.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoRoundsLeagueUT {
    TwoRoundsLeague two= new TwoRoundsLeague();
    @Test
    void toStringTest() {

        Assertions.assertTrue(two.toString().equals("Two rounds league"));
    }

    @Test
    void howManyRounds() {
        Assertions.assertTrue(two.howManyRounds()==2);
    }
}