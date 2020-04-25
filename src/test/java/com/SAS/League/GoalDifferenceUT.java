package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GoalDifferenceUT {

    @Test
    void toStringTest() {
        GoalDifference difference= new GoalDifference();
        Assert.assertTrue(difference.toString().equals("The bigger goal difference")); //need to solve it
    }


    @Test
    void tieBreaker() {
        GoalDifference difference= new GoalDifference();
        Assert.assertTrue(difference.tieBreaker().equals("Goal Difference"));
    }
}