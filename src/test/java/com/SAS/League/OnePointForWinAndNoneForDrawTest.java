package com.SAS.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;

class OnePointForWinAndNoneForDrawTest {
    OnePointForWinAndNoneForDraw onePointForWinAndNoneForDraw= new OnePointForWinAndNoneForDraw();
    @Test
    void toStringTest() {
        Assertions.assertTrue(onePointForWinAndNoneForDraw.toString().equals("One point per win and none for draw"));
    }

    @Test
    void howManyPointsForWin() {
        Assertions.assertTrue(onePointForWinAndNoneForDraw.howManyPointsForWin()==1);
    }

    @Test
    void howManyPointsForDraw() {
        Assertions.assertTrue(onePointForWinAndNoneForDraw.howManyPointsForDraw()==0);

    }
}