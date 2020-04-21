package com.SAS.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GoalDifferenceTest {

    @Test
    public String toString() {
        GoalDifference difference= new GoalDifference();
        Assertions.assertTrue(difference.toString().equals("The bigger goal difference"));
        return null;
    }


    @Test
    void tieBreaker() {
        GoalDifference difference= new GoalDifference();
        Assertions.assertTrue(difference.tieBreaker().equals("Goal Difference"));
    }
}