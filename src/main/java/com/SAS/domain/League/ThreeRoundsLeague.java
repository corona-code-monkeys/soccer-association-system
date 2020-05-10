package com.SAS.domain.League;

/**
 * class for three rounds league
 */
public class ThreeRoundsLeague extends GamesPolicy {

    public ThreeRoundsLeague() {
        this.name="Three rounds league";
    }

    /**
     * constructor that init the object and gives him name
     */
    public ThreeRoundsLeague(League league, Season season) {
this();
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
