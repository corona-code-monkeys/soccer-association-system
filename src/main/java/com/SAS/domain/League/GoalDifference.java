package com.SAS.domain.League;
/**
 * class for the policy of which we break tie according to which team has a bigger goal difference
 */
public class GoalDifference extends LeagueRankPolicy {

    public GoalDifference() {
        this.name="The bigger goal difference";
    }

    /**
     * constructor that init the object and gives him name
     */
    public GoalDifference(League league, Season season) {
        this.name="The bigger goal difference";
        this.season=season;
        this.league=league;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String tieBreaker() {
        return "Goal Difference";
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

