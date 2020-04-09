package com.SAS.League;
/**
 * class for the policy of which we break tie according to which team has a bigger goal difference
 */
public class GoalDifference extends LeagueRankPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public GoalDifference() {
        this.name="The bigger goal difference";
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String tieBreaker() {
        return "Goal Difference";
    }
}

