package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class OneRoundLeagueUT {
    OneRoundLeague one= new OneRoundLeague();
    @Test
    void toStringTest() {

        Assert.assertTrue(one.toString().equals("One round league"));
    }

    @Test
    void howManyRounds() {
        Assert.assertTrue(one.howManyRounds()==1);
    }
}