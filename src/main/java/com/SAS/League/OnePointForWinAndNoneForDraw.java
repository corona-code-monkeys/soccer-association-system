package com.SAS.League;

/**
 * class for policy that gives one point per win and none per draws
 */
public class OnePointForWinAndNoneForDraw extends PointsPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public OnePointForWinAndNoneForDraw() {
        this.name="one point per win and none for draw";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int howManyPointsForWin() {
        return 1;
    }

    @Override
    public int howManyPointsForDraw() {
        return 0;
    }
}
