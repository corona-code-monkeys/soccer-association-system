package com.SAS.domain.League;

/**
 * class for two rounds league
 */
public class TwoRoundsLeague extends GamesPolicy {

    public TwoRoundsLeague() {
        this.name = "Two rounds league";
    }

    /**
     * constructor that init the object and gives him name
     */
    public TwoRoundsLeague(League league, Season season) {
        this();
        this.league = league;
        this.season = season;
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
