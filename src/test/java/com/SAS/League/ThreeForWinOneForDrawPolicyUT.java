package com.SAS.League;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreeForWinOneForDrawPolicyUT {
    ThreeForWinOneForDrawPolicy three= new ThreeForWinOneForDrawPolicy();
    @Test
    void toStringTest() {
        Assert.assertTrue(three.toString().equals("Three points for win and one point for draw policy"));
    }

    @Test
    void howManyPointsForWin() {
        Assert.assertTrue(three.howManyPointsForWin()==3);
    }

    @Test
    void howManyPointsForDraw() {
        Assert.assertTrue(three.howManyPointsForDraw()==1);

    }
}