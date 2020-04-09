package com.SAS.League;
/**
 * class for the policy of which we break tie according to number of wins
 */
public class NumberOfWins extends LeagueRankPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public NumberOfWins() {
        this.name="Number of wins";
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String tieBreaker() {
        return "Wins";
    }
}
