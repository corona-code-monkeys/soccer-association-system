package com.SAS.League;

/**
 * class for one round league
 */
public class OneRoundLeague extends GamesPolicy {

    public OneRoundLeague() {
        this.name="One round league";
    }

    /**
     * constructor that init the object and gives him name
     */
    public OneRoundLeague(League league, Season season) {
        this.name="One round league";
        this.league=league;
        this.season=season;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int howManyRounds() {
        return 1;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Season getSeason() {
        return this.season;
    }

    @Override
    public League getLeague() {
        return this.league;
    }
}
