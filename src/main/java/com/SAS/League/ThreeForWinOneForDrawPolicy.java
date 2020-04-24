package com.SAS.League;

/**
 * class for policy that gives three points per win and one point per draws
 */
public class ThreeForWinOneForDrawPolicy extends PointsPolicy {

    public ThreeForWinOneForDrawPolicy() {
        this.name = "Three points for win and one point for draw policy";
    }

    /**
     * constructor that init the object and gives him name
     */
    public ThreeForWinOneForDrawPolicy(League league, Season season) {
        this();
        this.league = league;
        this.season = season;
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
