package com.SAS.League;

import com.SAS.team.Team;


public class Budget {
    private Team team;
    private Season season;
    private double amount;

    /**
     * GamesArrangement constructor with attributes
     *
     * @param team:   the team the budget belong
     * @param season: the budget's season
     * @param amount: the list of the games
     */
    public Budget(Team team, Season season, double amount) {
        this.team = team;
        this.season = season;
        this.amount = amount;

    }

    /**
     * @return the budget's team name
     */
    public Team getTeam() {
        return team;
    }


    /**
     * @return in which season the budget is valid
     */
    public Season getSeason() {
        return season;
    }


    /**
     * @return the size of the budget
     */
    public double getBudget() {
        return amount;
    }

    /**
     * @param amount the new budget you want to set
     */
    public void setBudget(int amount) {
        this.amount = amount;
    }
}
