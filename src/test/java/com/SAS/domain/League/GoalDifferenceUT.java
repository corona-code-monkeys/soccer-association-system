package com.SAS.domain.League;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GoalDifferenceUT {

    @Test
    void toStringTest() {
        GoalDifference difference= new GoalDifference();
        Assertions.assertTrue(difference.toString().equals("The bigger goal difference")); //need to solve it
    }


    @Test
    void tieBreaker() {
        GoalDifference difference= new GoalDifference();
        Assertions.assertTrue(difference.tieBreaker().equals("Goal Difference"));
    }
}