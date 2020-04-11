package com.SAS.League;

/**
 * class for two rounds league
 */
public class TwoRoundsLeague extends GamesPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public TwoRoundsLeague(League league, Season season) {
        this.name="Two rounds league";
        this.league=league;
        this.season=season;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int howManyRounds() {
        return 2;
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
