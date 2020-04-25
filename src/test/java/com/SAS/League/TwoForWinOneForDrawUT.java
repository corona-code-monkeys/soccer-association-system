package com.SAS.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TwoForWinOneForDrawUT {
    TwoForWinOneForDraw two = new TwoForWinOneForDraw();

    @Test
    void toStringTest() {
        Assertions.assertTrue(two.toString().equals("Two points for a win and one point for draw"));

    }

    @Test
    void howManyPointsForWin() {
        Assertions.assertTrue(two.howManyPointsForWin() == 2);
    }

    @Test
    void howManyPointsForDraw() {
        Assertions.assertTrue(two.howManyPointsForDraw() == 1);

    }
}