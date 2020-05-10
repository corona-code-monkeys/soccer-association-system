package com.SAS.domain.League;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ThreeForWinOneForDrawPolicyUT {
    ThreeForWinOneForDrawPolicy three= new ThreeForWinOneForDrawPolicy();
    @Test
    void toStringTest() {
        Assertions.assertTrue(three.toString().equals("Three points for win and one point for draw policy"));
    }

    @Test
    void howManyPointsForWin() {
        Assertions.assertTrue(three.howManyPointsForWin()==3);
    }

    @Test
    void howManyPointsForDraw() {
        Assertions.assertTrue(three.howManyPointsForDraw()==1);

    }
}