package com.SAS.League;

/**
 * class for policy that gives one point per win and none per draws
 */
public class OnePointForWinAndNoneForDraw extends PointsPolicy {

    public OnePointForWinAndNoneForDraw() {
        this.name="one point per win and none for draw";
    }

    /**
     * constructor that init the object and gives him name
     */
    public OnePointForWinAndNoneForDraw(League league, Season season) {
        this.name="one point per win and none for draw";
        this.league=league;
        this.season= season;
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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public League getLeague() {
        return this.league;
    }

    @Override
    public Season getSeason() {
        return this.season;
    }
}
