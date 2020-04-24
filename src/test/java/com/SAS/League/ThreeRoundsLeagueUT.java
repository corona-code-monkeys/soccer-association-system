package com.SAS.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreeRoundsLeagueUT {
    ThreeRoundsLeague three= new ThreeRoundsLeague();
    @Test
    void toStringTest() {

        Assertions.assertTrue(three.toString().equals("Three rounds league"));
    }

    @Test
    void howManyRounds() {
        Assertions.assertTrue(three.howManyRounds()==3);
    }
}