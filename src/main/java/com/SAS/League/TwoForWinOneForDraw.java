package com.SAS.League;

/**
 * class for policy that gives two points per win and one point per draws
 */
public class TwoForWinOneForDraw extends PointsPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public TwoForWinOneForDraw() {
        this.name = "Two point for a win and one point for draw";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int howManyPointsForWin() {
        return 2;
    }

    @Override
    public int howManyPointsForDraw() {
        return 1;
    }
}
