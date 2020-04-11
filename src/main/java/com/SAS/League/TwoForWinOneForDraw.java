package com.SAS.League;

/**
 * class for policy that gives two points per win and one point per draws
 */
public class TwoForWinOneForDraw extends PointsPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public TwoForWinOneForDraw(League league, Season season) {
        this.name = "Two point for a win and one point for draw";
        this.league = league;
        this.season = season;
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
