package com.SAS.League;

/**
 * class for the policy of which we break tie according to number of wins
 */
public class NumberOfWins extends LeagueRankPolicy {

    public NumberOfWins() {
        this.name = "Number of wins";
    }

    /**
     * constructor that init the object and gives him name
     */
    public NumberOfWins(League league, Season season) {
        this();
        this.league = league;
        this.season = season;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String tieBreaker() {
        return "Wins";
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
