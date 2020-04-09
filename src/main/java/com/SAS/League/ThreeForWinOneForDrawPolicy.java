package com.SAS.League;

/**
 * class for policy that gives three points per win and one point per draws
 */
public class ThreeForWinOneForDrawPolicy extends PointsPolicy{
    /**
     * constructor that init the object and gives him name
     */
    public ThreeForWinOneForDrawPolicy() {
        this.name="Three points for win and one point for draw policy";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int howManyPointsForWin() {
        return 3;
    }

    @Override
    public int howManyPointsForDraw() {
        return 1;
    }
}
