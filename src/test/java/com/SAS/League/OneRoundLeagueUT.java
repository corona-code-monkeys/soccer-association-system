package com.SAS.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OneRoundLeagueUT {
    OneRoundLeague one= new OneRoundLeague();
    @Test
    void toStringTest() {

        Assertions.assertTrue(one.toString().equals("One round league"));
    }

    @Test
    void howManyRounds() {
        Assertions.assertTrue(one.howManyRounds()==1);
    }
}