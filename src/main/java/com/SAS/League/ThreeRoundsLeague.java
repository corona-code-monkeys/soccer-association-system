package com.SAS.League;

/**
 * class for three rounds league
 */
public class ThreeRoundsLeague extends GamesPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public ThreeRoundsLeague(League league, Season season) {
        this.name="Three rounds league";
        this.league=league;
        this.season=season;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int howManyRounds() {
        return 3;
    }

    /**
     * @return the name of the policy
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the season object
     */
    @Override
    public Season getSeason() {
        return this.season;
    }

    /**
     * @return the league object
     */
    @Override
    public League getLeague() {
        return this.league;
    }
}
