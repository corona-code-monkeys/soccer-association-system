package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoForWinOneForDrawUT {
    TwoForWinOneForDraw two = new TwoForWinOneForDraw();

    @Test
    void toStringTest() {
        Assert.assertTrue(two.toString().equals("Two points for a win and one point for draw"));

    }

    @Test
    void howManyPointsForWin() {
        Assert.assertTrue(two.howManyPointsForWin() == 2);
    }

    @Test
    void howManyPointsForDraw() {
        Assert.assertTrue(two.howManyPointsForDraw() == 1);

    }
}