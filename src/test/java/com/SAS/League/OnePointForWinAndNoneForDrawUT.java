package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;

class OnePointForWinAndNoneForDrawUT {
    OnePointForWinAndNoneForDraw onePointForWinAndNoneForDraw= new OnePointForWinAndNoneForDraw();
    @Test
    void toStringTest() {
        Assert.assertTrue(onePointForWinAndNoneForDraw.toString().equals("One point per win and none for draw"));
    }

    @Test
    void howManyPointsForWin() {
        Assert.assertTrue(onePointForWinAndNoneForDraw.howManyPointsForWin()==1);
    }

    @Test
    void howManyPointsForDraw() {
        Assert.assertTrue(onePointForWinAndNoneForDraw.howManyPointsForDraw()==0);

    }
}